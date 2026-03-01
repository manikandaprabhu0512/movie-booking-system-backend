package com.manikanda.movie_ticket_booking_system.services.PaymentService.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manikanda.movie_ticket_booking_system.dto.PaymentDTO.PaymentCreateRequest;
import com.manikanda.movie_ticket_booking_system.dto.PaymentDTO.PaymentResponse;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Booking;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Booking.BookingStatus;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Payment;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Payment.PaymentStatus;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Payment.RefundStatus;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ShowsSeatsModel;
import com.manikanda.movie_ticket_booking_system.mapper.PaymentMapper;
import com.manikanda.movie_ticket_booking_system.repo.BookingRepo.BookingRepo;
import com.manikanda.movie_ticket_booking_system.repo.PaymentRepo.PaymentRepo;
import com.manikanda.movie_ticket_booking_system.services.PaymentService.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.model.checkout.Session;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final BookingRepo bookingRepo;
    private final PaymentMapper paymentMapper;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Override
    public PaymentResponse createPayment(PaymentCreateRequest request) {
        // Check for duplicate idempotency key
        if (paymentRepo.findByIdempotencyKey(request.getIdempotencyKey()).isPresent()) {
            throw new IllegalStateException("Payment with this idempotency key already exists");
        }

        // Fetch booking
        Booking booking = bookingRepo.findById(request.getBookingId())
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + request.getBookingId()));

        // Validate payment amount matches booking total
        if (!request.getPaymentAmount().equals(booking.getTotalPrice())) {
            throw new IllegalArgumentException("Payment amount does not match booking total price");
        }

        try {
            // Initialize Stripe
            Stripe.apiKey = stripeApiKey;

            // Create Stripe Checkout Session
            String transactionId = "TXN-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://your-app.com/payment/success?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl("https://your-app.com/payment/cancel")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(request.getCurrency() != null ? request.getCurrency() : "USD")
                                                    .setUnitAmount((long) (request.getPaymentAmount() * 100))
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Movie Booking - " + booking.getBookingReference())
                                                                    .setDescription("Booking Reference: " + booking.getBookingReference())
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .setQuantity(1L)
                                    .build()
                    )
                    .putMetadata("bookingId", booking.getId().toString())
                    .putMetadata("bookingReference", booking.getBookingReference())
                    .putMetadata("transactionId", transactionId)
                    .build();

            Session session = Session.create(params);

            // Create payment entity
            Payment payment = Payment.builder()
                    .booking(booking)
                    .transactionId(transactionId)
                    .idempotencyKey(request.getIdempotencyKey())
                    .paymentAmount(request.getPaymentAmount())
                    .paymentMethod(request.getPaymentMethod())
                    .currency(request.getCurrency() != null ? request.getCurrency() : "USD")
                    .paymentStatus(PaymentStatus.PENDING)
                    .gatewayReference(session.getId())
                    .gatewayResponse(session.toJson().toString())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .refundStatus(RefundStatus.NO_REFUND)
                    .build();

            Payment savedPayment = paymentRepo.save(payment);
            log.info("Stripe Checkout Session created successfully: {}", session.getId());

            // Return response with checkout URL
            PaymentResponse response = paymentMapper.toResponse(savedPayment);
            response.setCheckoutUrl(session.getUrl());
            return response;

        } catch (StripeException e) {
            log.error("Stripe Checkout Session creation failed: {}", e.getMessage());

            // Create failed payment record
            Payment failedPayment = Payment.builder()
                    .booking(booking)
                    .transactionId("TXN-FAILED-" + System.currentTimeMillis())
                    .idempotencyKey(request.getIdempotencyKey())
                    .paymentAmount(request.getPaymentAmount())
                    .paymentMethod(request.getPaymentMethod())
                    .currency(request.getCurrency() != null ? request.getCurrency() : "USD")
                    .paymentStatus(PaymentStatus.FAILED)
                    .failureReason(e.getUserMessage())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .refundStatus(RefundStatus.NO_REFUND)
                    .build();

            paymentRepo.save(failedPayment);
            return paymentMapper.toResponse(failedPayment);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPayment(Long paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + paymentId));
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByTransactionId(String transactionId) {
        Payment payment = paymentRepo.findByTransactionId(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with transaction id: " + transactionId));
        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByBookingId(Long bookingId) {
        Payment payment = paymentRepo.findByBookingId(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found for booking id: " + bookingId));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse confirmPayment(Long paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + paymentId));

        if (!payment.getPaymentStatus().equals(PaymentStatus.PENDING)) {
            throw new IllegalStateException("Only pending payments can be confirmed");
        }

        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setProcessedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());

        Payment updatedPayment = paymentRepo.save(payment);
        return paymentMapper.toResponse(updatedPayment);
    }

    @Override
    public PaymentResponse refundPayment(Long paymentId, Double refundAmount) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with id: " + paymentId));

        if (!payment.getPaymentStatus().equals(PaymentStatus.COMPLETED)) {
            throw new IllegalStateException("Only completed payments can be refunded");
        }

        if (refundAmount > payment.getPaymentAmount()) {
            throw new IllegalArgumentException("Refund amount cannot exceed original payment amount");
        }

        try {
            Stripe.apiKey = stripeApiKey;

            // Create Stripe refund
            RefundCreateParams params = RefundCreateParams.builder()
                    .setCharge(payment.getGatewayReference())
                    .setAmount((long) (refundAmount * 100)) // Convert to cents
                    .setReason(RefundCreateParams.Reason.REQUESTED_BY_CUSTOMER)
                    .build();

            Refund refund = Refund.create(params);

            // Update payment with refund info
            payment.setRefundAmount(refundAmount);
            payment.setRefundStatus(RefundStatus.COMPLETED);
            payment.setRefundedAt(LocalDateTime.now());
            payment.setPaymentStatus(PaymentStatus.REFUNDED);
            payment.setUpdatedAt(LocalDateTime.now());

            Payment updatedPayment = paymentRepo.save(payment);
            log.info("Refund processed successfully for payment: {}", paymentId);

            return paymentMapper.toResponse(updatedPayment);

        } catch (StripeException e) {
            log.error("Stripe refund failed: {}", e.getMessage());

            // Update refund status to FAILED
            payment.setRefundStatus(RefundStatus.FAILED);
            payment.setUpdatedAt(LocalDateTime.now());
            Payment updatedPayment = paymentRepo.save(payment);

            throw new RuntimeException("Refund failed: " + e.getUserMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPendingPayments() {
        List<Payment> pendingPayments = paymentRepo.findByPaymentStatus(PaymentStatus.PENDING);
        return pendingPayments.stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updatePaymentAndBooking(String sessionId) {
        try {
            // Find payment by session ID
            Payment payment = paymentRepo.findByGatewayReference(sessionId)
                    .orElseThrow(() -> new EntityNotFoundException("Payment not found for session: " + sessionId));

            log.info("Found payment: {}", payment.getId());

            // Update payment to COMPLETED
            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            payment.setProcessedAt(LocalDateTime.now());
            payment.setUpdatedAt(LocalDateTime.now());
            paymentRepo.save(payment);

            log.info("Payment marked as COMPLETED: {}", payment.getId());

            // Get associated booking
            Booking booking = payment.getBooking();
            if (booking == null) {
                throw new EntityNotFoundException("Booking not found for payment: " + payment.getId());
            }

            // Update booking to CONFIRMED
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            booking.setPaymentStatus(Booking.PaymentStatus.PAID);
            booking.setBookedAt(LocalDateTime.now());
            booking.setUpdatedAt(LocalDateTime.now());

            // Mark all seats as BOOKED
            if (booking.getBookedSeats() != null && !booking.getBookedSeats().isEmpty()) {
                booking.getBookedSeats().forEach(seat -> {
                    seat.setStatus(ShowsSeatsModel.BookingStatus.BOOKED);
                });
            }

            bookingRepo.save(booking);
            log.info("Booking confirmed and seats marked BOOKED: {}", booking.getId());

        } catch (EntityNotFoundException e) {
            log.error("Entity not found: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error updating payment/booking: {}", e.getMessage(), e);
        }
    }


}

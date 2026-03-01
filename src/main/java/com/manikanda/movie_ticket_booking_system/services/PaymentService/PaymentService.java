package com.manikanda.movie_ticket_booking_system.services.PaymentService;

import java.util.List;

import com.manikanda.movie_ticket_booking_system.dto.PaymentDTO.PaymentCreateRequest;
import com.manikanda.movie_ticket_booking_system.dto.PaymentDTO.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(PaymentCreateRequest request);

    PaymentResponse getPayment(Long paymentId);

    PaymentResponse getPaymentByTransactionId(String transactionId);

    PaymentResponse getPaymentByBookingId(Long bookingId);

    PaymentResponse confirmPayment(Long paymentId);

    PaymentResponse refundPayment(Long paymentId, Double refundAmount);

    List<PaymentResponse> getPendingPayments();

    void updatePaymentAndBooking(String sessionId);

}

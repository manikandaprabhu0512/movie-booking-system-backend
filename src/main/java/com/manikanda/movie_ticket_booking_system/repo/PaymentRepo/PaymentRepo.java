package com.manikanda.movie_ticket_booking_system.repo.PaymentRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manikanda.movie_ticket_booking_system.entity.Bookings.Payment;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Payment.PaymentStatus;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionId(String transactionId);

    Optional<Payment> findByIdempotencyKey(String idempotencyKey);

    Optional<Payment> findByBookingId(Long bookingId);

    List<Payment> findByBookingIdAndPaymentStatus(Long bookingId, PaymentStatus paymentStatus);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    Optional<Payment> findByGatewayReference(String gatewayReference);

}

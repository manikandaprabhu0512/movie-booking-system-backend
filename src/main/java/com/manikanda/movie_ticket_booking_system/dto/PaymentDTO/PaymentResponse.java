package com.manikanda.movie_ticket_booking_system.dto.PaymentDTO;

import java.time.LocalDateTime;

import com.manikanda.movie_ticket_booking_system.entity.Bookings.Payment.PaymentMethod;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Payment.PaymentStatus;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Payment.RefundStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long id;

    private Long bookingId;

    private String transactionId;

    private String idempotencyKey;

    private Double paymentAmount;

    private String currency;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String gatewayReference;

    private String checkoutUrl;

    private String failureReason;

    private RefundStatus refundStatus;

    private Double refundAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime processedAt;

    private LocalDateTime refundedAt;

}

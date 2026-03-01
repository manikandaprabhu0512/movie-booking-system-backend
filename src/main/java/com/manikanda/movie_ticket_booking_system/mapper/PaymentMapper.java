package com.manikanda.movie_ticket_booking_system.mapper;

import org.springframework.stereotype.Component;

import com.manikanda.movie_ticket_booking_system.dto.PaymentDTO.PaymentResponse;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Payment;

@Component
public class PaymentMapper {

    public PaymentResponse toResponse(Payment entity) {
        return PaymentResponse.builder()
                .id(entity.getId())
                .bookingId(entity.getBooking().getId())
                .transactionId(entity.getTransactionId())
                .idempotencyKey(entity.getIdempotencyKey())
                .paymentAmount(entity.getPaymentAmount())
                .currency(entity.getCurrency())
                .paymentMethod(entity.getPaymentMethod())
                .paymentStatus(entity.getPaymentStatus())
                .gatewayReference(entity.getGatewayReference())
                .failureReason(entity.getFailureReason())
                .refundStatus(entity.getRefundStatus())
                .refundAmount(entity.getRefundAmount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .processedAt(entity.getProcessedAt())
                .refundedAt(entity.getRefundedAt())
                .build();
    }

}

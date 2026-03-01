package com.manikanda.movie_ticket_booking_system.dto.PaymentDTO;

import com.manikanda.movie_ticket_booking_system.entity.Bookings.Payment.PaymentMethod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateRequest {

    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    @Positive(message = "Payment amount must be positive")
    @NotNull(message = "Payment amount is required")
    private Double paymentAmount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotBlank(message = "Idempotency key is required")
    private String idempotencyKey;

    private String currency;

    private String stripeTokenId;

    private String gatewayReference;

}

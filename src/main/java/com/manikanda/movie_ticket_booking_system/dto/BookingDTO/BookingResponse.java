package com.manikanda.movie_ticket_booking_system.dto.BookingDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import com.manikanda.movie_ticket_booking_system.entity.Bookings.Booking.BookingStatus;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Booking.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;

    private String bookingReference;

    private String idempotencyKey;

    private Long theatreId;

    private String theatreName;

    private Long showId;

    private String movieTitle;

    private LocalDate showDate;

    private LocalTime showTime;

    private Set<Long> bookedSeatIds;

    private Set<String> seatNumbers;

    private Double totalPrice;

    private BookingStatus bookingStatus;

    private PaymentStatus paymentStatus;

    private String customerEmail;

    private String customerPhone;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime bookedAt;

}

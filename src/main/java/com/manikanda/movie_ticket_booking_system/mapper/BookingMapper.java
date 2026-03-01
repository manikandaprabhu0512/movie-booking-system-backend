package com.manikanda.movie_ticket_booking_system.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.manikanda.movie_ticket_booking_system.dto.BookingDTO.BookingResponse;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Booking;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ShowsSeatsModel;

@Component
public class BookingMapper {

    public BookingResponse toResponse(Booking entity) {
        Set<Long> seatIds = entity.getBookedSeats().stream()
                .map(ShowsSeatsModel::getId)
                .collect(Collectors.toSet());

        Set<String> seatNumbers = entity.getBookedSeats().stream()
                .map(seat -> seat.getSeat().getRowName() + seat.getSeat().getSeatNumber())
                .collect(Collectors.toSet());

        return BookingResponse.builder()
                .id(entity.getId())
                .bookingReference(entity.getBookingReference())
                .idempotencyKey(entity.getIdempotencyKey())
                .theatreId(entity.getTheatre().getId())
                .theatreName(entity.getTheatre().getName())
                .showId(entity.getShow().getId())
                .movieTitle(entity.getShow().getMovie().getTitle())
                .showDate(entity.getShow().getShowDate())
                .showTime(entity.getShow().getShowTime())
                .bookedSeatIds(seatIds)
                .seatNumbers(seatNumbers)
                .totalPrice(entity.getTotalPrice())
                .bookingStatus(entity.getBookingStatus())
                .paymentStatus(entity.getPaymentStatus())
                .customerEmail(entity.getCustomerEmail())
                .customerPhone(entity.getCustomerPhone())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .bookedAt(entity.getBookedAt())
                .build();
    }

}

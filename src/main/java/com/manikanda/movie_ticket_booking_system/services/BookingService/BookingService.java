package com.manikanda.movie_ticket_booking_system.services.BookingService;

import java.util.List;

import com.manikanda.movie_ticket_booking_system.dto.BookingDTO.BookingCreateRequest;
import com.manikanda.movie_ticket_booking_system.dto.BookingDTO.BookingResponse;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Booking.PaymentStatus;

public interface BookingService {

    BookingResponse createBooking(BookingCreateRequest request);

    BookingResponse getBooking(Long bookingId);

    BookingResponse getBookingByReference(String bookingReference);

    BookingResponse getBookingByIdempotencyKey(String idempotencyKey);

    List<BookingResponse> getBookingsByCustomerEmail(String customerEmail);

    List<BookingResponse> getBookingsByShow(Long showId);

    BookingResponse updatePaymentStatus(Long bookingId, PaymentStatus paymentStatus);

    BookingResponse confirmBooking(Long bookingId);

    BookingResponse cancelBooking(Long bookingId);

}

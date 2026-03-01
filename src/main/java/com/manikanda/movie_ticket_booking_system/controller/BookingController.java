package com.manikanda.movie_ticket_booking_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manikanda.movie_ticket_booking_system.dto.BookingDTO.BookingCreateRequest;
import com.manikanda.movie_ticket_booking_system.dto.BookingDTO.BookingResponse;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Booking.PaymentStatus;
import com.manikanda.movie_ticket_booking_system.services.BookingService.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingCreateRequest request) {
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long bookingId) {
        BookingResponse response = bookingService.getBooking(bookingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reference/{bookingReference}")
    public ResponseEntity<BookingResponse> getBookingByReference(@PathVariable String bookingReference) {
        BookingResponse response = bookingService.getBookingByReference(bookingReference);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerEmail}")
    public ResponseEntity<List<BookingResponse>> getBookingsByCustomer(@PathVariable String customerEmail) {
        List<BookingResponse> responses = bookingService.getBookingsByCustomerEmail(customerEmail);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByShow(@PathVariable Long showId) {
        List<BookingResponse> responses = bookingService.getBookingsByShow(showId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{bookingId}/payment-status")
    public ResponseEntity<BookingResponse> updatePaymentStatus(
            @PathVariable Long bookingId,
            @RequestParam PaymentStatus paymentStatus) {
        BookingResponse response = bookingService.updatePaymentStatus(bookingId, paymentStatus);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(@PathVariable Long bookingId) {
        BookingResponse response = bookingService.confirmBooking(bookingId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long bookingId) {
        BookingResponse response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-idempotency")
    public ResponseEntity<BookingResponse> getBookingByIdempotencyKey(@RequestParam String idempotencyKey) {
        BookingResponse response = bookingService.getBookingByIdempotencyKey(idempotencyKey);
        return ResponseEntity.ok(response);
    }

}

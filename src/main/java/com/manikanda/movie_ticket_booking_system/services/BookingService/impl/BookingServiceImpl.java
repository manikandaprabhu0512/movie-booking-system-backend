package com.manikanda.movie_ticket_booking_system.services.BookingService.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manikanda.movie_ticket_booking_system.dto.BookingDTO.BookingCreateRequest;
import com.manikanda.movie_ticket_booking_system.dto.BookingDTO.BookingResponse;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Booking;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Booking.BookingStatus;
import com.manikanda.movie_ticket_booking_system.entity.Bookings.Booking.PaymentStatus;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ShowsSeatsModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.TheatreShowsModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.TheatreWriteModel;
import com.manikanda.movie_ticket_booking_system.mapper.BookingMapper;
import com.manikanda.movie_ticket_booking_system.repo.BookingRepo.BookingRepo;
import com.manikanda.movie_ticket_booking_system.repo.ShowsSeatsRepo.ShowsSeatsRepo;
import com.manikanda.movie_ticket_booking_system.repo.TheatreShowsRepo.TheatreShowsWriteRepo;
import com.manikanda.movie_ticket_booking_system.services.BookingService.BookingService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepo bookingRepo;
    private final TheatreShowsWriteRepo theatreShowsWriteRepo;
    private final ShowsSeatsRepo showsSeatsRepo;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingCreateRequest request) {
        if (bookingRepo.findByIdempotencyKey(request.getIdempotencyKey()).isPresent()) {
            throw new IllegalStateException("Booking with this idempotency key already exists");
        }
        TheatreShowsModel show = theatreShowsWriteRepo.findShowWithTheatre(request.getShowId())
        .orElseThrow(() -> new EntityNotFoundException("Show not found with id: " + request.getShowId()));

        TheatreWriteModel theatre = show.getScreenModel().getTheatre();

        Set<ShowsSeatsModel> bookedSeats = showsSeatsRepo.findByShowIdAndSeatIds(request.getShowId(), request.getShowSeatIds())
                .stream()
                .collect(Collectors.toSet());

        if (bookedSeats.size() != request.getShowSeatIds().size()) {
            throw new IllegalArgumentException("One or more selected seats are invalid for this show.");
        }

        if (bookedSeats.isEmpty()) {
            throw new EntityNotFoundException("No seats found with the given IDs");
        }

        bookedSeats.forEach(seat -> {
            if (!seat.getShow().getId().equals(request.getShowId())) {
                throw new IllegalArgumentException("Seat " + seat.getSeat().getId() + " does not belong to show " + request.getShowId());
            }
            if (!seat.getStatus().equals(ShowsSeatsModel.BookingStatus.AVAILABLE)) {
                throw new IllegalStateException("Seat " + seat.getId() + " is not available");
            }
            seat.setStatus(ShowsSeatsModel.BookingStatus.RESERVED);
        });

        showsSeatsRepo.saveAll(bookedSeats);

        Double totalPrice = bookedSeats.stream()
                .mapToDouble(seat -> seat.getSeat().getPrice())
                .sum();

        String bookingReference = generateBookingReference();
        Booking booking = Booking.builder()
                .bookingReference(bookingReference)
                .idempotencyKey(request.getIdempotencyKey())
                .theatre(theatre)
                .show(show)
                .bookedSeats(bookedSeats)
                .totalPrice(totalPrice)
                .bookingStatus(BookingStatus.PENDING)
                .paymentStatus(PaymentStatus.UNPAID)
                .customerEmail(request.getCustomerEmail())
                .customerPhone(request.getCustomerPhone())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Booking savedBooking = bookingRepo.save(booking);
        return bookingMapper.toResponse(savedBooking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + bookingId));
        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBookingByReference(String bookingReference) {
        Booking booking = bookingRepo.findByBookingReference(bookingReference)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with reference: " + bookingReference));
        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBookingByIdempotencyKey(String idempotencyKey) {
        Booking booking = bookingRepo.findByIdempotencyKey(idempotencyKey)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with idempotency key: " + idempotencyKey));
        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByCustomerEmail(String customerEmail) {
        List<Booking> bookings = bookingRepo.findByCustomerEmail(customerEmail);
        if (bookings.isEmpty()) {
            throw new EntityNotFoundException("No bookings found for customer: " + customerEmail);
        }
        return bookings.stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByShow(Long showId) {
        List<Booking> bookings = bookingRepo.findByShowId(showId);
        if (bookings.isEmpty()) {
            throw new EntityNotFoundException("No bookings found for show: " + showId);
        }
        return bookings.stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse updatePaymentStatus(Long bookingId, PaymentStatus paymentStatus) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + bookingId));

        booking.setPaymentStatus(paymentStatus);
        booking.setUpdatedAt(LocalDateTime.now());
        Booking updatedBooking = bookingRepo.save(booking);

        return bookingMapper.toResponse(updatedBooking);
    }

    @Override
    public BookingResponse confirmBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + bookingId));

        if (!booking.getBookingStatus().equals(BookingStatus.PENDING)) {
            throw new IllegalStateException("Only pending bookings can be confirmed");
        }

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setBookedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        // Update seat statuses to BOOKED
        booking.getBookedSeats().forEach(seat -> {
            seat.setStatus(ShowsSeatsModel.BookingStatus.BOOKED);
        });

        Booking updatedBooking = bookingRepo.save(booking);
        return bookingMapper.toResponse(updatedBooking);
    }

    @Override
    public BookingResponse cancelBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + bookingId));

        if (booking.getBookingStatus().equals(BookingStatus.CANCELLED)) {
            throw new IllegalStateException("Booking is already cancelled");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.setUpdatedAt(LocalDateTime.now());

        // Release seats back to AVAILABLE
        booking.getBookedSeats().forEach(seat -> {
            seat.setStatus(ShowsSeatsModel.BookingStatus.AVAILABLE);
        });

        Booking updatedBooking = bookingRepo.save(booking);
        return bookingMapper.toResponse(updatedBooking);
    }

    private String generateBookingReference() {
        return "BK" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

}

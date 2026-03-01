package com.manikanda.movie_ticket_booking_system.repo.BookingRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manikanda.movie_ticket_booking_system.entity.Bookings.Booking;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingReference(String bookingReference);

    Optional<Booking> findByIdempotencyKey(String idempotencyKey);

    List<Booking> findByCustomerEmail(String customerEmail);

    List<Booking> findByShowId(Long showId);

}

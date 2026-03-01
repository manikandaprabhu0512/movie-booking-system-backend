package com.manikanda.movie_ticket_booking_system.repo.ShowsSeatsRepo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.manikanda.movie_ticket_booking_system.entity.Theatre.ShowsSeatsModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ShowsSeatsModel.BookingStatus;

import jakarta.persistence.LockModeType;
@Repository
public interface ShowsSeatsRepo extends JpaRepository<ShowsSeatsModel, Long> {
    
    @Query("SELECT DISTINCT s FROM ShowsSeatsModel s JOIN FETCH s.show sh JOIN FETCH s.seat WHERE s.show.id = :showId")
    List<ShowsSeatsModel> findByShowId(@Param("showId") Long showId);

    @Query("SELECT DISTINCT s FROM ShowsSeatsModel s JOIN FETCH s.show sh JOIN FETCH s.seat WHERE s.show.id = :showId AND s.status = :status")
    List<ShowsSeatsModel> findByShowIdAndStatus(@Param("showId") Long showId, @Param("status") BookingStatus status);

    @Query("SELECT s.seat.id FROM ShowsSeatsModel s WHERE s.show.id = :showId")
    Set<Long> findSeatIdsByShowId(@Param("showId") Long showId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ShowsSeatsModel s WHERE s.show.id = :showId AND s.seat.id IN :seatIds")
    Set<ShowsSeatsModel> findByShowIdAndSeatIds(
        @Param("showId") Long showId, 
        @Param("seatIds") Set<Long> seatIds
    );
}

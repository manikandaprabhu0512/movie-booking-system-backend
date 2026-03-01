package com.manikanda.movie_ticket_booking_system.repo.TheatreShowsRepo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.manikanda.movie_ticket_booking_system.entity.Theatre.TheatreShowsModel;

@Repository
public interface TheatreShowsWriteRepo extends JpaRepository<TheatreShowsModel, Long>{

    @Query("SELECT s FROM TheatreShowsModel s " +
       "WHERE s.screenModel.id = :screenId " +
       "AND s.showDate = :showDate " +
       "AND ((:startTime BETWEEN s.showTime AND s.endTime) " +
       "OR (:endTime BETWEEN s.showTime AND s.endTime) " +
       "OR (s.showTime BETWEEN :startTime AND :endTime))")
    List<TheatreShowsModel> findOverlappingShows(
        @Param("screenId") Long screenId, 
        @Param("showDate") LocalDate showDate, 
        @Param("startTime") LocalTime startTime, 
        @Param("endTime") LocalTime endTime
    );

    @Query("SELECT s FROM TheatreShowsModel s " +
       "JOIN FETCH s.screenModel scr " +
       "JOIN FETCH scr.theatre t " +
       "WHERE s.id = :showId")
    Optional<TheatreShowsModel> findShowWithTheatre(@Param("showId") Long showId);

    @Query("SELECT DISTINCT s FROM TheatreShowsModel s " +
           "JOIN FETCH s.screenModel scr " +
           "JOIN FETCH scr.theatre t " +
           "JOIN FETCH s.movie m " +
           "WHERE s.showDate > :currentDate " +
           "OR (s.showDate = :currentDate AND s.showTime >= :currentTime) " +
           "ORDER BY s.showDate ASC, s.showTime ASC")
    List<TheatreShowsModel> findAvailableShows(
        @Param("currentDate") LocalDate currentDate,
        @Param("currentTime") LocalTime currentTime
    );
}

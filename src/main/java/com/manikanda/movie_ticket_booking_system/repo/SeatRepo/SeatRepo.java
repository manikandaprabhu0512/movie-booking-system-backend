package com.manikanda.movie_ticket_booking_system.repo.SeatRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manikanda.movie_ticket_booking_system.entity.Theatre.SeatModel;

@Repository
public interface SeatRepo extends JpaRepository<SeatModel, Long> {

    List<SeatModel> findByScreenId(Long screenId);

}

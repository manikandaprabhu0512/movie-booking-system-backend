package com.manikanda.movie_ticket_booking_system.repo.ScreenRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manikanda.movie_ticket_booking_system.entity.Theatre.ScreenModel;

@Repository
public interface ScreenRepo extends JpaRepository<ScreenModel, Long> {
    Optional<ScreenModel> findById(Long screenId);
}

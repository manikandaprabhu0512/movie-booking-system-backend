package com.manikanda.movie_ticket_booking_system.repo.TheatreRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manikanda.movie_ticket_booking_system.entity.Theatre.TheatreWriteModel;

public interface TheatreWriteRepo extends JpaRepository<TheatreWriteModel, Long>{

    Optional<TheatreWriteModel> findById(Long id);

}

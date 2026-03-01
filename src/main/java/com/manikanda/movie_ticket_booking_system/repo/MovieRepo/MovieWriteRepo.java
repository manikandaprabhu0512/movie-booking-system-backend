package com.manikanda.movie_ticket_booking_system.repo.MovieRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manikanda.movie_ticket_booking_system.entity.Movies.MoviesWriteModel;

@Repository
public interface MovieWriteRepo extends JpaRepository<MoviesWriteModel, Long> {

    Optional<MoviesWriteModel> findById(Long id);
}

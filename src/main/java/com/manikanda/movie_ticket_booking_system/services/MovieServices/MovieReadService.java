package com.manikanda.movie_ticket_booking_system.services.MovieServices;

import java.util.List;

import com.manikanda.movie_ticket_booking_system.dto.MovieDTO.MovieResponse;

public interface MovieReadService {

    MovieResponse getMovieById(Long id);

    List<MovieResponse> getAllMovies();
}

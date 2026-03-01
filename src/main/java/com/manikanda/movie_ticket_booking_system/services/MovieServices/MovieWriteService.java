package com.manikanda.movie_ticket_booking_system.services.MovieServices;

import com.manikanda.movie_ticket_booking_system.dto.MovieDTO.MovieRequest;
import com.manikanda.movie_ticket_booking_system.dto.MovieDTO.MovieResponse;

public interface MovieWriteService {

    MovieResponse addMovie(MovieRequest movieRequest);

    MovieResponse updateMovie(Long id, MovieRequest movieRequest);

    void deleteMovie(Long id);
}

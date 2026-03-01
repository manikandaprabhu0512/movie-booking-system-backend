package com.manikanda.movie_ticket_booking_system.mapper;

import org.springframework.stereotype.Component;

import com.manikanda.movie_ticket_booking_system.dto.MovieDTO.MovieRequest;
import com.manikanda.movie_ticket_booking_system.dto.MovieDTO.MovieResponse;
import com.manikanda.movie_ticket_booking_system.entity.Movies.MoviesWriteModel;

@Component
public class MovieMapper {

    public MoviesWriteModel toWriteModel(MovieRequest movieRequest) {
        return MoviesWriteModel.builder()
            .title(movieRequest.getTitle())
            .language(movieRequest.getLanguage())
            .genre(movieRequest.getGenre())
            .durationMinutes(movieRequest.getDurationMinutes())
            .releaseDate(movieRequest.getReleaseDate())
            .certificate(movieRequest.getCertificate())
            .description(movieRequest.getDescription())
            .build();
    }

    public MovieResponse toResponse(MoviesWriteModel savedMovie) {
        return MovieResponse.builder()
            .id(savedMovie.getId())
            .title(savedMovie.getTitle())
            .language(savedMovie.getLanguage())
            .genre(savedMovie.getGenre())
            .durationMinutes(savedMovie.getDurationMinutes())
            .releaseDate(savedMovie.getReleaseDate())
            .certificate(savedMovie.getCertificate())
            .description(savedMovie.getDescription())
            .createdAt(savedMovie.getCreatedAt())
            .updatedAt(savedMovie.getUpdatedAt())
            .build();
    }

}

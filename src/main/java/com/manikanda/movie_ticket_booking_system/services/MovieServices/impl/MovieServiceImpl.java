package com.manikanda.movie_ticket_booking_system.services.MovieServices.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import com.manikanda.movie_ticket_booking_system.dto.MovieDTO.MovieRequest;
import com.manikanda.movie_ticket_booking_system.dto.MovieDTO.MovieResponse;
import com.manikanda.movie_ticket_booking_system.entity.Movies.MoviesWriteModel;
import com.manikanda.movie_ticket_booking_system.mapper.MovieMapper;
import com.manikanda.movie_ticket_booking_system.repo.MovieRepo.MovieWriteRepo;
import com.manikanda.movie_ticket_booking_system.services.MovieServices.MovieService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    
    private final MovieWriteRepo movieWriteRepo;
    private final MovieMapper movieMapper;
    
    @Override
    public MovieResponse addMovie(MovieRequest movieRequest) {
        MoviesWriteModel movieWriteModel = movieMapper.toWriteModel(movieRequest);
        MoviesWriteModel savedMovie = movieWriteRepo.save(movieWriteModel);
        return movieMapper.toResponse(savedMovie);
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        MoviesWriteModel movie = movieWriteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + id));
        return movieMapper.toResponse(movie);
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        return movieWriteRepo.findAll()
                .stream()
                .map(movieMapper::toResponse)
                .toList();
    }

    @Override
    public MovieResponse updateMovie(Long id, MovieRequest movieRequest) {
        MoviesWriteModel movie = movieWriteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + id));

        movie.setTitle(movieRequest.getTitle());
        movie.setLanguage(movieRequest.getLanguage());
        movie.setGenre(movieRequest.getGenre());
        movie.setDurationMinutes(movieRequest.getDurationMinutes());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        movie.setCertificate(movieRequest.getCertificate());
        movie.setDescription(movieRequest.getDescription());

        MoviesWriteModel savedMovie = movieWriteRepo.save(movie);
        return movieMapper.toResponse(savedMovie);
    }

    @Override
    public void deleteMovie(Long id) {
        MoviesWriteModel movie = movieWriteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with id: " + id));
        movieWriteRepo.delete(movie);
    }
}

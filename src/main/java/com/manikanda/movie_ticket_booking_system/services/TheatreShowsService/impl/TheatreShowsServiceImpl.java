package com.manikanda.movie_ticket_booking_system.services.TheatreShowsService.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manikanda.movie_ticket_booking_system.dto.TheatreShowDTO.TheatreShowRequest;
import com.manikanda.movie_ticket_booking_system.dto.TheatreShowDTO.TheatreShowResponse;
import com.manikanda.movie_ticket_booking_system.entity.Movies.MoviesWriteModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ScreenModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.TheatreShowsModel;
import com.manikanda.movie_ticket_booking_system.mapper.TheatreShowsMapper;
import com.manikanda.movie_ticket_booking_system.repo.MovieRepo.MovieWriteRepo;
import com.manikanda.movie_ticket_booking_system.repo.ScreenRepo.ScreenRepo;
import com.manikanda.movie_ticket_booking_system.repo.TheatreShowsRepo.TheatreShowsWriteRepo;
import com.manikanda.movie_ticket_booking_system.services.ShowsSeatsService.ShowsSeatsService;
import com.manikanda.movie_ticket_booking_system.services.TheatreShowsService.TheatreShowsService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TheatreShowsServiceImpl implements TheatreShowsService{

    private final TheatreShowsWriteRepo theatreShowsWriteRepo;
    private final ScreenRepo screenRepo;
    private final MovieWriteRepo movieWriteRepo;
    private final TheatreShowsMapper theatreShowsMapper;
    private final ShowsSeatsService showsSeatsService;

    @Override
    @Transactional
    public TheatreShowResponse addMovie(TheatreShowRequest request) {
        ScreenModel screenModel = screenRepo.findById(request.getScreenId())
                                            .orElseThrow(() -> new EntityNotFoundException("Screen not found screen id: " + request.getScreenId()));
        
        MoviesWriteModel moviesWriteModel = movieWriteRepo.findById(request.getMovieId())
                                                            .orElseThrow(() -> new EntityNotFoundException("Movie Not found for id: " + request.getMovieId()));

        LocalTime startTime = request.getShowTime();
        LocalTime endTime = startTime.plusMinutes(moviesWriteModel.getDurationMinutes() + 20);

        List<TheatreShowsModel> overlaps = theatreShowsWriteRepo.findOverlappingShows(
            request.getScreenId(), request.getShowDate(), startTime, endTime
        );

        if (!overlaps.isEmpty()) {
            throw new IllegalStateException("Screen is already occupied during this time slot!");
        }

        TheatreShowsModel model = theatreShowsMapper.toEntity(screenModel, moviesWriteModel, request);

        TheatreShowsModel savedModel = theatreShowsWriteRepo.save(model);
        showsSeatsService.addSeats(savedModel.getId());

        return theatreShowsMapper.toResponse(savedModel);
    }

    @Override
    public List<TheatreShowResponse> getAvailableShows() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        List<TheatreShowsModel> availableShows = theatreShowsWriteRepo.findAvailableShows(currentDate, currentTime);
        return availableShows.stream()
                .map(theatreShowsMapper::toResponse)
                .toList();
    }

}

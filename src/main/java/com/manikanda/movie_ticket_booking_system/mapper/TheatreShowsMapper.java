package com.manikanda.movie_ticket_booking_system.mapper;

import org.springframework.stereotype.Component;

import com.manikanda.movie_ticket_booking_system.dto.TheatreShowDTO.TheatreShowRequest;
import com.manikanda.movie_ticket_booking_system.dto.TheatreShowDTO.TheatreShowResponse;
import com.manikanda.movie_ticket_booking_system.entity.Movies.MoviesWriteModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ScreenModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.TheatreShowsModel;

@Component
public class TheatreShowsMapper {

    public TheatreShowsModel toEntity(ScreenModel screenModel, 
            MoviesWriteModel moviesWriteModel, TheatreShowRequest request) {
        return TheatreShowsModel.builder()
                .screenModel(screenModel)
                .movie(moviesWriteModel)
                .showTime(request.getShowTime())
                .endTime(request.getShowTime().plusMinutes(moviesWriteModel.getDurationMinutes() + 20))
                .showDate(request.getShowDate())
                .build();
    }

    public TheatreShowResponse toResponse(TheatreShowsModel savedModel) {
        return TheatreShowResponse.builder()
                .id(savedModel.getId())
                .theatreId(savedModel.getScreenModel().getTheatre().getId())
                .theatreName(savedModel.getScreenModel().getTheatre().getName())
                .movieId(savedModel.getMovie().getId())
                .movieTitle(savedModel.getMovie().getTitle())
                .screenName(savedModel.getScreenModel().getName())
                .showTime(savedModel.getShowTime())
                .endTime(savedModel.getEndTime())
                .showDate(savedModel.getShowDate())
                .createdAt(savedModel.getCreatedAt())
                .updatedAt(savedModel.getUpdatedAt())
                .build();
    }

    

}

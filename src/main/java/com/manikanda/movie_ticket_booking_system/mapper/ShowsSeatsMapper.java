package com.manikanda.movie_ticket_booking_system.mapper;

import org.springframework.stereotype.Component;

import com.manikanda.movie_ticket_booking_system.dto.ShowsSeatsDTO.ShowsSeatsResponse;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.SeatModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ShowsSeatsModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.TheatreShowsModel;

@Component
public class ShowsSeatsMapper {

    public ShowsSeatsModel toEntity(TheatreShowsModel show, SeatModel seat) {
        ShowsSeatsModel.ShowsSeatsModelBuilder builder = ShowsSeatsModel.builder()
                .show(show)
                .seat(seat);

        return builder.build();
    }

    public ShowsSeatsResponse toResponse(ShowsSeatsModel entity) {
        return ShowsSeatsResponse.builder()
                .id(entity.getId())
                .showId(entity.getShow().getId())
                .seatId(entity.getSeat().getId())
                .rowName(entity.getSeat().getRowName())
                .seatNumber(entity.getSeat().getSeatNumber())
                .seatType(entity.getSeat().getSeatType().toString())
                .price(entity.getSeat().getPrice())
                .status(entity.getStatus())
                .build();
    }

}

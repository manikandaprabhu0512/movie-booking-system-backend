package com.manikanda.movie_ticket_booking_system.mapper;

import org.springframework.stereotype.Component;

import com.manikanda.movie_ticket_booking_system.dto.ScreenDTO.ScreenRequest;
import com.manikanda.movie_ticket_booking_system.dto.ScreenDTO.ScreenResponse;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ScreenModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.TheatreWriteModel;

@Component
public class ScreenMapper {

    public ScreenModel toEntity(ScreenRequest request, TheatreWriteModel theatre) {
        return ScreenModel.builder()
                .name(request.getName())
                .theatre(theatre)
                .build();
    }

    public ScreenResponse toResponse(ScreenModel entity) {
        return ScreenResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .theatreId(entity.getTheatre().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}

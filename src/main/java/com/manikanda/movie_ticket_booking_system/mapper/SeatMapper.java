package com.manikanda.movie_ticket_booking_system.mapper;

import org.springframework.stereotype.Component;

import com.manikanda.movie_ticket_booking_system.dto.SeatDTO.SeatRequest;
import com.manikanda.movie_ticket_booking_system.dto.SeatDTO.SeatResponse;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ScreenModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.SeatModel;

@Component
public class SeatMapper {

    public SeatModel toEntity(SeatRequest request, ScreenModel screen) {
        SeatModel.SeatModelBuilder builder = SeatModel.builder()
                .screen(screen)
                .rowName(request.getRowName())
                .seatNumber(request.getSeatNumber());
        
        if (request.getSeatType() != null) {
            builder.seatType(request.getSeatType());
        }
        
        return builder.build();
    }

    public SeatResponse toResponse(SeatModel entity) {
        return SeatResponse.builder()
                .id(entity.getId())
                .screenId(entity.getScreen().getId())
                .rowName(entity.getRowName())
                .seatNumber(entity.getSeatNumber())
                .seatType(entity.getSeatType())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}

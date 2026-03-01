package com.manikanda.movie_ticket_booking_system.mapper;

import org.springframework.stereotype.Component;

import com.manikanda.movie_ticket_booking_system.dto.TheatreDTO.TheatreRequest;
import com.manikanda.movie_ticket_booking_system.dto.TheatreDTO.TheatreResponse;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.TheatreWriteModel;

@Component
public class TheatreMapper {

    public TheatreWriteModel toEntity(TheatreRequest request) {
        return TheatreWriteModel.builder()
                .name(request.getName())
                .city(request.getCity())
                .addressLineOne(request.getAddressLineOne())
                .addressLineTwo(request.getAddressLineTwo())
                .addressLineThree(request.getAddressLineThree())
                .state(request.getState())
                .pincode(request.getPincode())
                .build();
    }

    public TheatreResponse toResponse(TheatreWriteModel entity) {
        return TheatreResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .city(entity.getCity())
                .addressLineOne(entity.getAddressLineOne())
                .addressLineTwo(entity.getAddressLineTwo())
                .addressLineThree(entity.getAddressLineThree())
                .state(entity.getState())
                .pincode(entity.getPincode())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}

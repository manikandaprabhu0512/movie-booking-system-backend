package com.manikanda.movie_ticket_booking_system.services.TheatreServices;

import java.util.List;

import com.manikanda.movie_ticket_booking_system.dto.TheatreDTO.TheatreResponse;

public interface TheatreReadService {

    TheatreResponse getTheatreById(Long id);

    List<TheatreResponse> getAllTheatres();
}

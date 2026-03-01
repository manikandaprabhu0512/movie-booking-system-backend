package com.manikanda.movie_ticket_booking_system.services.TheatreShowsService;

import java.util.List;

import com.manikanda.movie_ticket_booking_system.dto.TheatreShowDTO.TheatreShowResponse;

public interface TheatreShowReadService {

    List<TheatreShowResponse> getAvailableShows();
}

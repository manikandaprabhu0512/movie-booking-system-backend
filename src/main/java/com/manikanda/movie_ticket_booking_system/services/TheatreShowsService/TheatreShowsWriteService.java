package com.manikanda.movie_ticket_booking_system.services.TheatreShowsService;

import com.manikanda.movie_ticket_booking_system.dto.TheatreShowDTO.TheatreShowRequest;
import com.manikanda.movie_ticket_booking_system.dto.TheatreShowDTO.TheatreShowResponse;

public interface TheatreShowsWriteService {

    TheatreShowResponse addMovie(TheatreShowRequest request);

}

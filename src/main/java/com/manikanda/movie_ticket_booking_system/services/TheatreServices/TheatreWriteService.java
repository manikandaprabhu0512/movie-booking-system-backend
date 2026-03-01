package com.manikanda.movie_ticket_booking_system.services.TheatreServices;

import com.manikanda.movie_ticket_booking_system.dto.TheatreDTO.TheatreRequest;
import com.manikanda.movie_ticket_booking_system.dto.TheatreDTO.TheatreResponse;

public interface TheatreWriteService {
    TheatreResponse addTheatre(TheatreRequest request);

    TheatreResponse updateTheatre(Long id, TheatreRequest request);

    void deleteTheatre(Long id);
}

package com.manikanda.movie_ticket_booking_system.services.ScreenService;

import com.manikanda.movie_ticket_booking_system.dto.ScreenDTO.ScreenRequest;
import com.manikanda.movie_ticket_booking_system.dto.ScreenDTO.ScreenResponse;

public interface ScreenService {
    ScreenResponse addScreen(ScreenRequest request);
    ScreenResponse getScreenById(Long id);
}

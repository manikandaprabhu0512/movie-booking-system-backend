package com.manikanda.movie_ticket_booking_system.services.SeatService;

import com.manikanda.movie_ticket_booking_system.dto.SeatDTO.SeatRequest;
import com.manikanda.movie_ticket_booking_system.dto.SeatDTO.SeatResponse;

public interface SeatService {
    SeatResponse addSeat(SeatRequest request);
    SeatResponse getSeatById(Long id);
}

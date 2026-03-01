package com.manikanda.movie_ticket_booking_system.services.ShowsSeatsService;

import java.util.List;

import com.manikanda.movie_ticket_booking_system.dto.ShowsSeatsDTO.ShowsSeatsRequest;
import com.manikanda.movie_ticket_booking_system.dto.ShowsSeatsDTO.ShowsSeatsResponse;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ShowsSeatsModel.BookingStatus;

public interface ShowsSeatsService {
    
    ShowsSeatsResponse addShowSeat(ShowsSeatsRequest request);

    boolean addSeats(Long showId);
    
    List<ShowsSeatsResponse> getShowSeats(Long showId);
    
    List<ShowsSeatsResponse> getAvailableSeats(Long showId);
    
    ShowsSeatsResponse updateSeatStatus(Long showSeatId, BookingStatus status);
    
    ShowsSeatsResponse getShowSeat(Long showSeatId);
    
    void deleteShowSeat(Long showSeatId);

}

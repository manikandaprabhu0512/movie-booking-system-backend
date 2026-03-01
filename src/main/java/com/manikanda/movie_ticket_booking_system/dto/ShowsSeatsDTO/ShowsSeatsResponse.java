package com.manikanda.movie_ticket_booking_system.dto.ShowsSeatsDTO;

import com.manikanda.movie_ticket_booking_system.entity.Theatre.ShowsSeatsModel.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowsSeatsResponse {

    private Long id;

    private Long showId;

    private Long seatId;

    private String rowName;

    private Integer seatNumber;

    private String seatType;

    private Double price;

    private BookingStatus status;

}

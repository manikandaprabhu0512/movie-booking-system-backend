package com.manikanda.movie_ticket_booking_system.dto.SeatDTO;

import java.time.LocalDateTime;

import com.manikanda.movie_ticket_booking_system.entity.Theatre.SeatModel.SeatType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {

    private Long id;
    private Long screenId;
    private String rowName;
    private Integer seatNumber;
    private SeatType seatType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

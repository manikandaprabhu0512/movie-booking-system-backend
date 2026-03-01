package com.manikanda.movie_ticket_booking_system.dto.SeatDTO;

import com.manikanda.movie_ticket_booking_system.entity.Theatre.SeatModel.SeatType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {

    @NotNull(message = "Screen ID is required")
    private Long screenId;

    @NotBlank(message = "Row name is required")
    private String rowName;

    @NotNull(message = "Seat number is required")
    private Integer seatNumber;

    private SeatType seatType;

}

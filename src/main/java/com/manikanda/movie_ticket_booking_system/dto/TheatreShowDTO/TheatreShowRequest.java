package com.manikanda.movie_ticket_booking_system.dto.TheatreShowDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TheatreShowRequest {
    
    @NotNull(message = "MovieId is required")
    private Long movieId;

    @NotNull(message = "Screen id is required")
    private Long screenId;

    @NotNull(message = "Show time is required")
    private LocalTime showTime;

    @NotNull(message = "Show date is required")
    private LocalDate showDate;
}

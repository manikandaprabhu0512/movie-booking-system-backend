package com.manikanda.movie_ticket_booking_system.dto.TheatreShowDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheatreShowResponse {

    private Long id;
    private Long theatreId;
    private String theatreName;
    private Long movieId;
    private String movieTitle;
    private String screenName;
    private LocalTime showTime;
    private LocalTime endTime;
    private LocalDate showDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

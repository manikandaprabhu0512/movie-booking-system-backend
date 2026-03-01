package com.manikanda.movie_ticket_booking_system.dto.MovieDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class MovieResponse {

    private Long id;
    private String title;
    private String language;
    private String genre;
    private Integer durationMinutes;
    private LocalDate releaseDate;
    private String certificate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

package com.manikanda.movie_ticket_booking_system.dto.MovieDTO;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {

    @NotBlank(message = "Movie title is required")
    private String title;
    @NotBlank(message = "Language is required")
    private String language;
    @NotBlank(message = "Genre is required")
    private String genre;
    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationMinutes;
    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;
    @NotBlank(message = "Certificate is required")
    private String certificate;
    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

}

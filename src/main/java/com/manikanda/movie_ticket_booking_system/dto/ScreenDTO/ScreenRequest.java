package com.manikanda.movie_ticket_booking_system.dto.ScreenDTO;

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
public class ScreenRequest {

    @NotNull(message = "Theatre ID is required")
    private Long theatreId;

    @NotBlank(message = "Screen name is required")
    private String name;

}

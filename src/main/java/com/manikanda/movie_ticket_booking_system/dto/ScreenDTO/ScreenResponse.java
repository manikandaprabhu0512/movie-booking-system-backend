package com.manikanda.movie_ticket_booking_system.dto.ScreenDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreenResponse {

    private Long id;
    private String name;
    private Long theatreId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

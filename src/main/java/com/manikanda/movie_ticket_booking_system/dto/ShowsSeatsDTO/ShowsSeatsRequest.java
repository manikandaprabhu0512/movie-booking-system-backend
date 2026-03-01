package com.manikanda.movie_ticket_booking_system.dto.ShowsSeatsDTO;

import com.manikanda.movie_ticket_booking_system.entity.Theatre.ShowsSeatsModel.BookingStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowsSeatsRequest {

    @NotNull(message = "Show ID is required")
    private Long showId;

    @NotNull(message = "Price is required")
    private Double price;

    private BookingStatus status;

}

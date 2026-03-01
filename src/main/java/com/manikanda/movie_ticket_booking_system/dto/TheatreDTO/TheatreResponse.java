package com.manikanda.movie_ticket_booking_system.dto.TheatreDTO;

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
public class TheatreResponse {

    private Long id;
    private String name;
    private String city;
    private String addressLineOne;
    private String addressLineTwo;
    private String addressLineThree;
    private String state;
    private String pincode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

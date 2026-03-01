package com.manikanda.movie_ticket_booking_system.dto.TheatreDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TheatreRequest {

    @NotBlank(message = "Theatre name is required")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Address Line 1 is required")
    private String addressLineOne;

    @NotBlank(message = "Address Line 2 is required")
    private String addressLineTwo;

    private String addressLineThree;

    @NotBlank(message = "State is Required")
    private String state;

    @NotBlank(message = "Pincode is Required")
    private String pincode;

}

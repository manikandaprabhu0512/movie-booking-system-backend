package com.manikanda.movie_ticket_booking_system.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.manikanda.movie_ticket_booking_system.dto.TheatreDTO.TheatreResponse;
import com.manikanda.movie_ticket_booking_system.exception.GlobalExceptionHandler;
import com.manikanda.movie_ticket_booking_system.services.TheatreServices.TheatreService;

class TheatreControllerTest {

    private MockMvc mockMvc;
    private TheatreService theatreService;

    @BeforeEach
    void setUp() {
        theatreService = mock(TheatreService.class);
        TheatreController controller = new TheatreController(theatreService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createTheatreReturnsCreated() throws Exception {
        TheatreResponse response = TheatreResponse.builder().id(1L).name("PVR Orion").build();
        when(theatreService.addTheatre(any())).thenReturn(response);

        mockMvc.perform(post("/theatre/add-theatre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validTheatreJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("PVR Orion"));
    }

    @Test
    void getAllTheatresReturnsOk() throws Exception {
        when(theatreService.getAllTheatres()).thenReturn(List.of(
                TheatreResponse.builder().id(1L).name("PVR").build(),
                TheatreResponse.builder().id(2L).name("INOX").build()));

        mockMvc.perform(get("/theatre"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("PVR"))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void updateTheatreReturnsOk() throws Exception {
        TheatreResponse response = TheatreResponse.builder().id(2L).name("INOX Forum").build();
        when(theatreService.updateTheatre(eq(2L), any())).thenReturn(response);

        mockMvc.perform(put("/theatre/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validTheatreJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("INOX Forum"));
    }

    @Test
    void deleteTheatreReturnsNoContent() throws Exception {
        doNothing().when(theatreService).deleteTheatre(3L);

        mockMvc.perform(delete("/theatre/3"))
                .andExpect(status().isNoContent());

        verify(theatreService).deleteTheatre(3L);
    }

    private String validTheatreJson() {
        return """
                {
                  "name": "PVR Orion",
                  "city": "Bengaluru",
                  "addressLineOne": "Mall Road",
                  "addressLineTwo": "Rajajinagar",
                  "addressLineThree": "Landmark",
                  "state": "Karnataka",
                  "pincode": "560010"
                }
                """;
    }
}

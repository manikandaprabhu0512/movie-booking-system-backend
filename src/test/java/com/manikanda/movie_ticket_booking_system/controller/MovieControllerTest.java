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

import com.manikanda.movie_ticket_booking_system.dto.MovieDTO.MovieResponse;
import com.manikanda.movie_ticket_booking_system.exception.GlobalExceptionHandler;
import com.manikanda.movie_ticket_booking_system.services.MovieServices.MovieService;

class MovieControllerTest {

    private MockMvc mockMvc;
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieService = mock(MovieService.class);
        MovieController controller = new MovieController(movieService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createMovieReturnsCreated() throws Exception {
        MovieResponse response = MovieResponse.builder().id(1L).title("Interstellar").build();
        when(movieService.addMovie(any())).thenReturn(response);

        mockMvc.perform(post("/movies/add-movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validMovieJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Interstellar"));
    }

    @Test
    void getAllMoviesReturnsOk() throws Exception {
        when(movieService.getAllMovies()).thenReturn(List.of(
                MovieResponse.builder().id(1L).title("Interstellar").build(),
                MovieResponse.builder().id(2L).title("Dune").build()));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].title").value("Dune"));
    }

    @Test
    void updateMovieReturnsOk() throws Exception {
        MovieResponse response = MovieResponse.builder().id(4L).title("Interstellar 2").build();
        when(movieService.updateMovie(eq(4L), any())).thenReturn(response);

        mockMvc.perform(put("/movies/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validMovieJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.title").value("Interstellar 2"));
    }

    @Test
    void deleteMovieReturnsNoContent() throws Exception {
        doNothing().when(movieService).deleteMovie(8L);

        mockMvc.perform(delete("/movies/8"))
                .andExpect(status().isNoContent());

        verify(movieService).deleteMovie(8L);
    }

    private String validMovieJson() {
        return """
                {
                  "title": "Interstellar",
                  "language": "English",
                  "genre": "Sci-Fi",
                  "durationMinutes": 169,
                  "releaseDate": "2014-11-07",
                  "certificate": "PG-13",
                  "description": "Space exploration drama"
                }
                """;
    }
}

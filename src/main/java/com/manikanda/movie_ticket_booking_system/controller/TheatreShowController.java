package com.manikanda.movie_ticket_booking_system.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manikanda.movie_ticket_booking_system.dto.TheatreShowDTO.TheatreShowRequest;
import com.manikanda.movie_ticket_booking_system.dto.TheatreShowDTO.TheatreShowResponse;
import com.manikanda.movie_ticket_booking_system.services.TheatreShowsService.TheatreShowsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/theatre-shows")
@RequiredArgsConstructor
public class TheatreShowController {

    private final TheatreShowsService theatreShowsService;

    @PostMapping("/add-show")
    public ResponseEntity<TheatreShowResponse> addShow(@Valid @RequestBody TheatreShowRequest theatreShowRequest) {
        TheatreShowResponse response = theatreShowsService.addMovie(theatreShowRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<List<TheatreShowResponse>> getAvailableShows() {
        List<TheatreShowResponse> responses = theatreShowsService.getAvailableShows();
        return ResponseEntity.ok(responses);
    }

}

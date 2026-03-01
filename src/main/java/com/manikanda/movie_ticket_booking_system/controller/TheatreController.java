package com.manikanda.movie_ticket_booking_system.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manikanda.movie_ticket_booking_system.dto.TheatreDTO.TheatreRequest;
import com.manikanda.movie_ticket_booking_system.dto.TheatreDTO.TheatreResponse;
import com.manikanda.movie_ticket_booking_system.services.TheatreServices.TheatreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/theatre")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @PostMapping("/add-theatre")
    public ResponseEntity<TheatreResponse> addTheatre(@Valid @RequestBody TheatreRequest request) {
        TheatreResponse response = theatreService.addTheatre(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TheatreResponse>> getAllTheatres() {
        List<TheatreResponse> responses = theatreService.getAllTheatres();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheatreResponse> getTheatre(@PathVariable Long id) {
        TheatreResponse response = theatreService.getTheatreById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TheatreResponse> updateTheatre(
            @PathVariable Long id,
            @Valid @RequestBody TheatreRequest request) {
        TheatreResponse response = theatreService.updateTheatre(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheatre(@PathVariable Long id) {
        theatreService.deleteTheatre(id);
        return ResponseEntity.noContent().build();
    }

}

package com.manikanda.movie_ticket_booking_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manikanda.movie_ticket_booking_system.dto.ScreenDTO.ScreenRequest;
import com.manikanda.movie_ticket_booking_system.dto.ScreenDTO.ScreenResponse;
import com.manikanda.movie_ticket_booking_system.services.ScreenService.ScreenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping
    public ResponseEntity<ScreenResponse> createScreen(@Valid @RequestBody ScreenRequest request) {
        ScreenResponse response = screenService.addScreen(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreenResponse> getScreenById(@PathVariable Long id) {
        ScreenResponse response = screenService.getScreenById(id);
        return ResponseEntity.ok(response);
    }

}

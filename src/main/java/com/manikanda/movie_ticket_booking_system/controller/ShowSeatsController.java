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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manikanda.movie_ticket_booking_system.dto.ShowsSeatsDTO.ShowsSeatsRequest;
import com.manikanda.movie_ticket_booking_system.dto.ShowsSeatsDTO.ShowsSeatsResponse;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ShowsSeatsModel.BookingStatus;
import com.manikanda.movie_ticket_booking_system.services.ShowsSeatsService.ShowsSeatsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/show-seats")
@RequiredArgsConstructor
public class ShowSeatsController {

    private final ShowsSeatsService showsSeatsService;

    @PostMapping("/add")
    public ResponseEntity<ShowsSeatsResponse> addShowSeat(@Valid @RequestBody ShowsSeatsRequest request) {
        ShowsSeatsResponse response = showsSeatsService.addShowSeat(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<ShowsSeatsResponse>> getShowSeats(@PathVariable Long showId) {
        List<ShowsSeatsResponse> response = showsSeatsService.getShowSeats(showId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/show/{showId}/available")
    public ResponseEntity<List<ShowsSeatsResponse>> getAvailableSeats(@PathVariable Long showId) {
        List<ShowsSeatsResponse> response = showsSeatsService.getAvailableSeats(showId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowsSeatsResponse> getShowSeat(@PathVariable Long id) {
        ShowsSeatsResponse response = showsSeatsService.getShowSeat(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ShowsSeatsResponse> updateSeatStatus(
            @PathVariable Long id,
            @RequestParam BookingStatus status) {
        ShowsSeatsResponse response = showsSeatsService.updateSeatStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowSeat(@PathVariable Long id) {
        showsSeatsService.deleteShowSeat(id);
        return ResponseEntity.noContent().build();
    }

}

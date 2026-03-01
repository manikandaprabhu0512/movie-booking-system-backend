package com.manikanda.movie_ticket_booking_system.services.ShowsSeatsService.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.manikanda.movie_ticket_booking_system.dto.ShowsSeatsDTO.ShowsSeatsRequest;
import com.manikanda.movie_ticket_booking_system.dto.ShowsSeatsDTO.ShowsSeatsResponse;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.SeatModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ShowsSeatsModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ShowsSeatsModel.BookingStatus;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.TheatreShowsModel;
import com.manikanda.movie_ticket_booking_system.mapper.ShowsSeatsMapper;
import com.manikanda.movie_ticket_booking_system.repo.SeatRepo.SeatRepo;
import com.manikanda.movie_ticket_booking_system.repo.ShowsSeatsRepo.ShowsSeatsRepo;
import com.manikanda.movie_ticket_booking_system.repo.TheatreShowsRepo.TheatreShowsWriteRepo;
import com.manikanda.movie_ticket_booking_system.services.ShowsSeatsService.ShowsSeatsService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowsSeatsServiceImpl implements ShowsSeatsService {

    private final ShowsSeatsRepo showsSeatsRepo;
    private final TheatreShowsWriteRepo theatreShowsWriteRepo;
    private final SeatRepo seatRepo;
    private final ShowsSeatsMapper showsSeatsMapper;

    @Override
    public ShowsSeatsResponse addShowSeat(ShowsSeatsRequest request) {
        // TheatreShowsModel show = theatreShowsWriteRepo.findById(request.getShowId())
        //         .orElseThrow(() -> new EntityNotFoundException("There is no show found with id: " + request.getShowId()));

        // SeatModel seat = seatRepo.findById(request.getSeatId())
        //         .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + request.getSeatId()));

        // showsSeatsRepo.findByShowIdAndSeatId(request.getShowId(), request.getSeatId())
        //         .ifPresent(existing -> {
        //             throw new IllegalArgumentException("This seat already exists for the given show");
        //         });

        // ShowsSeatsModel showSeat = showsSeatsMapper.toEntity(request, show, seat);
        // ShowsSeatsModel savedShowSeat = showsSeatsRepo.save(showSeat);

        // return showsSeatsMapper.toResponse(savedShowSeat);
        return null;
    }

    @Override
    public List<ShowsSeatsResponse> getShowSeats(Long showId) {
        List<ShowsSeatsModel> showSeats = showsSeatsRepo.findByShowId(showId);
        if (showSeats.isEmpty()) {
            throw new EntityNotFoundException("No seats found for show with id: " + showId);
        }
        return showSeats.stream()
                .map(showsSeatsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShowsSeatsResponse> getAvailableSeats(Long showId) {
        List<ShowsSeatsModel> availableSeats = showsSeatsRepo.findByShowIdAndStatus(showId, BookingStatus.AVAILABLE);
        if (availableSeats.isEmpty()) {
            throw new EntityNotFoundException("No available seats found for show with id: " + showId);
        }
        return availableSeats.stream()
                .map(showsSeatsMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ShowsSeatsResponse updateSeatStatus(Long showSeatId, BookingStatus status) {
        ShowsSeatsModel showSeat = showsSeatsRepo.findById(showSeatId)
                .orElseThrow(() -> new EntityNotFoundException("Show seat not found with id: " + showSeatId));

        showSeat.setStatus(status);
        ShowsSeatsModel updatedShowSeat = showsSeatsRepo.save(showSeat);

        return showsSeatsMapper.toResponse(updatedShowSeat);
    }

    @Override
    public ShowsSeatsResponse getShowSeat(Long showSeatId) {
        ShowsSeatsModel showSeat = showsSeatsRepo.findById(showSeatId)
                .orElseThrow(() -> new EntityNotFoundException("Show seat not found with id: " + showSeatId));

        return showsSeatsMapper.toResponse(showSeat);
    }

    @Override
    public void deleteShowSeat(Long showSeatId) {
        if (!showsSeatsRepo.existsById(showSeatId)) {
            throw new EntityNotFoundException("Show seat not found with id: " + showSeatId);
        }
        showsSeatsRepo.deleteById(showSeatId);
    }

    @Override
    public boolean addSeats(Long showId) {
        TheatreShowsModel show = theatreShowsWriteRepo.findById(showId)
                .orElseThrow(() -> new EntityNotFoundException("There is no show found with id: " + showId));

        System.out.println("Show:" + show); 
        
        List<SeatModel> seats = seatRepo.findByScreenId(show.getScreenModel().getId());

        if (seats == null || seats.isEmpty()) {
            throw new EntityNotFoundException("No seats found for screen with id: " + show.getScreenModel().getId());
        }

        Set<Long> existingSeatIds = showsSeatsRepo.findSeatIdsByShowId(show.getId());

        List<ShowsSeatsModel> toSave = seats.stream()
            .filter(seat -> !existingSeatIds.contains(seat.getId()))
            .map(seat -> showsSeatsMapper.toEntity(show, seat))
            .collect(Collectors.toList());

        if (toSave.isEmpty()) return false;

        showsSeatsRepo.saveAll(toSave);
        return true;
    }

}

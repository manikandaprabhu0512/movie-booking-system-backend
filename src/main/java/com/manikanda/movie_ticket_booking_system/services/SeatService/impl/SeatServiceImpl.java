package com.manikanda.movie_ticket_booking_system.services.SeatService.impl;

import org.springframework.stereotype.Service;

import com.manikanda.movie_ticket_booking_system.dto.SeatDTO.SeatRequest;
import com.manikanda.movie_ticket_booking_system.dto.SeatDTO.SeatResponse;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ScreenModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.SeatModel;
import com.manikanda.movie_ticket_booking_system.mapper.SeatMapper;
import com.manikanda.movie_ticket_booking_system.repo.ScreenRepo.ScreenRepo;
import com.manikanda.movie_ticket_booking_system.repo.SeatRepo.SeatRepo;
import com.manikanda.movie_ticket_booking_system.services.SeatService.SeatService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepo seatRepo;
    private final ScreenRepo screenRepo;
    private final SeatMapper seatMapper;

    @Override
    public SeatResponse addSeat(SeatRequest request) {
        ScreenModel screen = screenRepo.findById(request.getScreenId())
                .orElseThrow(() -> new EntityNotFoundException("Screen not found with id: " + request.getScreenId()));

        SeatModel seat = seatMapper.toEntity(request, screen);
        SeatModel savedSeat = seatRepo.save(seat);

        return seatMapper.toResponse(savedSeat);
    }

    @Override
    public SeatResponse getSeatById(Long id) {
        SeatModel seat = seatRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with id: " + id));
        return seatMapper.toResponse(seat);
    }

}

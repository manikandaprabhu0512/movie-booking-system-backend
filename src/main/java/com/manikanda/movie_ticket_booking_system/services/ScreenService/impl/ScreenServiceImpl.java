package com.manikanda.movie_ticket_booking_system.services.ScreenService.impl;

import org.springframework.stereotype.Service;

import com.manikanda.movie_ticket_booking_system.dto.ScreenDTO.ScreenRequest;
import com.manikanda.movie_ticket_booking_system.dto.ScreenDTO.ScreenResponse;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.ScreenModel;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.TheatreWriteModel;
import com.manikanda.movie_ticket_booking_system.mapper.ScreenMapper;
import com.manikanda.movie_ticket_booking_system.repo.ScreenRepo.ScreenRepo;
import com.manikanda.movie_ticket_booking_system.repo.TheatreRepo.TheatreWriteRepo;
import com.manikanda.movie_ticket_booking_system.services.ScreenService.ScreenService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepo screenRepo;
    private final TheatreWriteRepo theatreRepo;
    private final ScreenMapper screenMapper;

    @Override
    public ScreenResponse addScreen(ScreenRequest request) {
        TheatreWriteModel theatre = theatreRepo.findById(request.getTheatreId())
                .orElseThrow(() -> new EntityNotFoundException("Theatre not found with id: " + request.getTheatreId()));

        

        ScreenModel screen = screenMapper.toEntity(request, theatre);
        ScreenModel savedScreen = screenRepo.save(screen);

        return screenMapper.toResponse(savedScreen);
    }

    @Override
    public ScreenResponse getScreenById(Long id) {
        ScreenModel screen = screenRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Screen not found with id: " + id));
        return screenMapper.toResponse(screen);
    }

}

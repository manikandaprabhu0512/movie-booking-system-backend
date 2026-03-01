package com.manikanda.movie_ticket_booking_system.services.TheatreServices.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import com.manikanda.movie_ticket_booking_system.dto.TheatreDTO.TheatreRequest;
import com.manikanda.movie_ticket_booking_system.dto.TheatreDTO.TheatreResponse;
import com.manikanda.movie_ticket_booking_system.entity.Theatre.TheatreWriteModel;
import com.manikanda.movie_ticket_booking_system.mapper.TheatreMapper;
import com.manikanda.movie_ticket_booking_system.repo.TheatreRepo.TheatreWriteRepo;
import com.manikanda.movie_ticket_booking_system.services.TheatreServices.TheatreService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TheatreServiceImpl implements TheatreService{

    private final TheatreWriteRepo theatreWriteRepo;
    private final TheatreMapper theatreMapper;

    @Override
    public TheatreResponse addTheatre(TheatreRequest request) {
        TheatreWriteModel writeModel = theatreMapper.toEntity(request);
        TheatreWriteModel savedModel = theatreWriteRepo.save(writeModel);
        return theatreMapper.toResponse(savedModel);
    }

    @Override
    public TheatreResponse getTheatreById(Long id) {
        TheatreWriteModel theatre = theatreWriteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Theatre not found with id: " + id));
        return theatreMapper.toResponse(theatre);
    }

    @Override
    public List<TheatreResponse> getAllTheatres() {
        return theatreWriteRepo.findAll()
                .stream()
                .map(theatreMapper::toResponse)
                .toList();
    }

    @Override
    public TheatreResponse updateTheatre(Long id, TheatreRequest request) {
        TheatreWriteModel theatre = theatreWriteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Theatre not found with id: " + id));

        theatre.setName(request.getName());
        theatre.setCity(request.getCity());
        theatre.setAddressLineOne(request.getAddressLineOne());
        theatre.setAddressLineTwo(request.getAddressLineTwo());
        theatre.setAddressLineThree(request.getAddressLineThree());
        theatre.setState(request.getState());
        theatre.setPincode(request.getPincode());

        TheatreWriteModel saved = theatreWriteRepo.save(theatre);
        return theatreMapper.toResponse(saved);
    }

    @Override
    public void deleteTheatre(Long id) {
        TheatreWriteModel theatre = theatreWriteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Theatre not found with id: " + id));
        theatreWriteRepo.delete(theatre);
    }

}

package com.manikanda.movie_ticket_booking_system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/public/health")
public class HealthController {
    @GetMapping
    public String healthCheck() {
        return "Hello World!!";
    }
    
}

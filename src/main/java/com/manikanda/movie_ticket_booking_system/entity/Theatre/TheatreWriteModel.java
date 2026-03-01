package com.manikanda.movie_ticket_booking_system.entity.Theatre;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "theatre", 
        indexes = {
        @Index(name = "idx_theatre_name", columnList = "name"),
        @Index(name = "idx_theatre_city", columnList = "city"),
        @Index(name = "idx_state_state", columnList = "state")
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TheatreWriteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Theatre name is required")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Address Line 1 is required")
    private String addressLineOne;

    @NotBlank(message = "Address Line 2 is required")
    private String addressLineTwo;

    private String addressLineThree;

    @NotBlank(message = "State is Required")
    private String state;

    @NotBlank(message = "Pincode is Required")
    private String pincode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
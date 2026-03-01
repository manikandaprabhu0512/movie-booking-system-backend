package com.manikanda.movie_ticket_booking_system.entity.Theatre;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.manikanda.movie_ticket_booking_system.entity.Movies.MoviesWriteModel;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Index;

@Entity
@Table(name = "theatre_shows", indexes = {
    @Index(name = "idx_show_date", columnList = "showDate"),
    @Index(name = "idx_movie_date", columnList = "movie_id, showDate")
    }, uniqueConstraints = {
    @UniqueConstraint(name = "uk_screen_date_time", columnNames = {"screen_id", "showDate", "showTime"})
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheatreShowsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private ScreenModel screenModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private MoviesWriteModel movie;

    @NotNull(message = "Show time is required")
    private LocalTime showTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotNull(message = "Show date is required")
    private LocalDate showDate;

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

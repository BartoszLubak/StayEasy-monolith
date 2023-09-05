package com.example.stayeasymonolith.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID uuid;
    @ManyToOne
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Guest guest;
}

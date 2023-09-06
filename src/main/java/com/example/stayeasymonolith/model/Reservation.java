package com.example.stayeasymonolith.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;
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
    @Column(columnDefinition = "DATE")
    private LocalDate checkIn;
    @Column(columnDefinition = "DATE")
    private LocalDate checkOut;
    @OneToMany (mappedBy = "reservation", cascade = CascadeType.ALL)
    private List <Guest> guest;
}

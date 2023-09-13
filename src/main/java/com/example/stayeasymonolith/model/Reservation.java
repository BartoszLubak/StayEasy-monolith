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
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;
    @ManyToMany(mappedBy = "reservations")
    private List<Room> rooms;
    @Column(columnDefinition = "DATE")
    private LocalDate checkIn;
    @Column(columnDefinition = "DATE")
    private LocalDate checkOut;
    @OneToMany (mappedBy = "reservation")
    private List <Guest> guest;
}

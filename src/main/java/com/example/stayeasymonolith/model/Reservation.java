package com.example.stayeasymonolith.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;
    @ManyToMany(mappedBy = "reservations")
    private List<Room> rooms;
    @Column(columnDefinition = "DATE")
    @FutureOrPresent
    private LocalDate checkIn;
    @Column(columnDefinition = "DATE")
    @FutureOrPresent
    private LocalDate checkOut;
    @OneToMany (mappedBy = "reservation")
    private List <Guest> guest;
}

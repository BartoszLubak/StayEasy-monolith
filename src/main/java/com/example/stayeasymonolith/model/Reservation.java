package com.example.stayeasymonolith.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany(mappedBy = "reservations")
    private List<Room> rooms;
    @Column(columnDefinition = "DATE")
    @FutureOrPresent
    private LocalDate checkIn;
    @Column(columnDefinition = "DATE")
    @FutureOrPresent
    private LocalDate checkOut;
    @OneToMany(mappedBy = "reservation")
    private List<Guest> guest;
    private BigDecimal reservationCost;
    @ManyToMany(mappedBy = "reservations")
    private List<Extra> extras;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

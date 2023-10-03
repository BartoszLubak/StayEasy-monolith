package com.example.stayeasymonolith.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int roomNumber;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    private RoomType roomType;
    private int roomCapacity;
    @ManyToMany (cascade = CascadeType.REMOVE)
    @JoinTable(
            name="reservation_room",
            joinColumns = @JoinColumn(name="room_id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id")
    )
    private List<Reservation> reservations;
    @ManyToMany
    @JoinTable(
            name="extras_room",
            joinColumns = @JoinColumn(name="room_id"),
            inverseJoinColumns = @JoinColumn(name = "extras_id")
    )
    private List<Extra> extras;
    private BigDecimal cost;
}

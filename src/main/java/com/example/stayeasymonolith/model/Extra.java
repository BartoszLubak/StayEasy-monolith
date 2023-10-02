package com.example.stayeasymonolith.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Extra {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private BigDecimal cost;
    @ManyToMany (mappedBy = "extras")
    private List<Room> roomList;
    @ManyToMany
    @JoinTable(name="extra_reservation",
            joinColumns = @JoinColumn(name="extra_id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id"))
    private List<Reservation> reservations;
}

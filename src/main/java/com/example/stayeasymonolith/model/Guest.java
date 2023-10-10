package com.example.stayeasymonolith.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Guest {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Embedded
    private Name name;
    private boolean child;
    @ManyToOne(cascade = CascadeType.REMOVE) //TODO change to @ManyToMany
    @JoinColumn (name = "reservation_id")
    private Reservation reservation;
}

package com.example.stayeasymonolith.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Guest {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;
    @Embedded
    private Name name;
    private boolean child;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "reservation_id")
    private Reservation reservation;
}

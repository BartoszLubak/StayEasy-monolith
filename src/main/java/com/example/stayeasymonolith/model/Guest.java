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
    @ManyToOne
    @JoinColumn (name = "guest_id")
    private Reservation reservation;
}

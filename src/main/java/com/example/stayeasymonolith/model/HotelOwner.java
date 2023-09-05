package com.example.stayeasymonolith.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class HotelOwner {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;

}

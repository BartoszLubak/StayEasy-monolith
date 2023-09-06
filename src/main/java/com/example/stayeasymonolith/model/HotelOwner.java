package com.example.stayeasymonolith.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.EmbeddableInstantiatorRegistrations;

import java.util.List;

@Entity
@NoArgsConstructor
public class HotelOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToMany(mappedBy = "hotelOwner")
    private List<Hotel> hotels;
    @Embedded
    private Name name;
}

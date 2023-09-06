package com.example.stayeasymonolith.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Min(5)
    private String name;
    @Embedded
    private Address address;
    private int stars;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;
    @ManyToOne
    @JoinColumn(name = "hotel_owner_id")
    private HotelOwner hotelOwner;
}

package com.example.stayeasymonolith.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Address {
    private String country;
    private String city;
    private String streetName;
    private String number;

}

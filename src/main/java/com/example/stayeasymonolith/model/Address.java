package com.example.stayeasymonolith.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String country;
    private String state;
    private String streetName;
    private String number;
}

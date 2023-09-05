package com.example.stayeasymonolith.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class GuestName {
    private String firstName;
    private String lastName;
    private GuestType guestType;

    public GuestName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public GuestName(String firstName, String lastName, GuestType guestType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.guestType = guestType;
    }
}

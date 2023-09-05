package com.example.stayeasymonolith.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Name {
    private String firstName;
    private String lastName;
    private GuestType guestType;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Name(String firstName, String lastName, GuestType guestType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.guestType = guestType;
    }
}

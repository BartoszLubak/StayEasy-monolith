package com.example.stayeasymonolith.model;

public enum Type {
    REGULAR,
    BUSINESS,
    PREMIUM,
    HOTEL_ADMIN,
    ADMIN;
    public static Type getDefault() {
        return REGULAR;
    }
}

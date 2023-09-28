package com.example.stayeasymonolith.model;

public enum Type {
    REGULAR,
    BUSINESS,
    PREMIUM,
    OWNER;

    public static Type getDefault() {
        return REGULAR;
    }
}

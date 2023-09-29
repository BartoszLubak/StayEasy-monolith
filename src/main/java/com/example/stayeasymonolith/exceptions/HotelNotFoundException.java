package com.example.stayeasymonolith.exceptions;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException(String message){
        super(message);
    }
}
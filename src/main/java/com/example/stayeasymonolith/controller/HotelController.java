package com.example.stayeasymonolith.controller;

import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/api/hotels/{city}")
    public Page<Hotel>getHotelsByAddress_City(@PathVariable String city, Pageable pageable){
        return hotelService.findHotelsByAddress_City(pageable, city);
    }

    @GetMapping("/api/hotels")
    public Page<Hotel> findAllHotels(Pageable pageable){
        return hotelService.findAllHotels(pageable);
    }
}

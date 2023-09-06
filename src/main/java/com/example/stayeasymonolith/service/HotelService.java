package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.repository.HotelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Page<Hotel> findHotelsByAddress_City(Pageable pageable, String city) {
        return hotelRepository.findHotelsByAddress_City(pageable, city);
    }

    public Page<Hotel> findAllHotels(Pageable pageable){
        return hotelRepository.findAll(pageable);
    }
}

package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.exceptions.HotelNotFoundException;
import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.repository.HotelRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Page<Hotel> findHotelsByNameOrAddress_City(Pageable pageable, String name, String city) {
        boolean nameCheck = !name.isEmpty() || !name.isBlank();
        boolean cityCheck = !city.isEmpty() || !city.isBlank();
        Page<Hotel> hotels;
        if(nameCheck && cityCheck){
            hotels = hotelRepository.findAllByNameAndAddress_City(pageable, name, city);
        } else if (!nameCheck) {
            hotels = hotelRepository.findHotelsByAddress_City(pageable, city);
        } else {
            hotels = hotelRepository.findHotelsByName(pageable, name);
        }
        throwHotelNotFoundExceptionWhenHotelListIsEmpty(hotels);
        return hotels;
    }

    public Page<Hotel> findAllHotels(Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.findAll(pageable);
        throwHotelNotFoundExceptionWhenHotelListIsEmpty(hotels);
        return hotels;
    }

    public Page<Hotel> findAllHotelsByHotelOwner(Pageable pageable, String hotelOwner) {
        Page<Hotel> hotels = hotelRepository.findHotelsByHotelOwner(pageable, hotelOwner);
        throwHotelNotFoundExceptionWhenHotelListIsEmpty(hotels);
        return hotels;
    }

    public Hotel findById(long id) {
        return hotelRepository.findHotelById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel with given id: %s do not exist".formatted(id)));
    }

    private void throwHotelNotFoundExceptionWhenHotelListIsEmpty(Page<Hotel> hotels) {
        if (hotels.isEmpty()) {
            throw new HotelNotFoundException("Hotel list is empty.");
        }
    }

    @Transactional
    public Hotel saveHotel(Hotel hotel) {
        hotelRepository.save(hotel);
        return hotel;
    }

    @Transactional
    public void deleteHotel (Hotel hotel){
        hotelRepository.delete(hotel);
    }
}

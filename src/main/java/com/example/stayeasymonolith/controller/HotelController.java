package com.example.stayeasymonolith.controller;

import com.example.stayeasymonolith.exceptions.HotelNotFoundException;
import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels-search")
    public String getHotelsByNameOrAddress_City(@RequestParam String name,
                                                @RequestParam String city,
                                                @SortDefault("name") Pageable pageable,
                                                Model model) {
        Page<Hotel> hotels = hotelService.findHotelsByNameOrAddress_City(pageable, name, city);
        model.addAttribute("hotels", hotels);
        return "/hotel/hotels";
    }

    @GetMapping("/hotels-search-by-owner")
    public String getHotelsByHotelOwner(@RequestParam String hotelOwner,
                                        @SortDefault("name") Pageable pageable,
                                        Model model) {
        Page<Hotel> hotels = hotelService.findAllHotelsByHotelOwner(pageable, hotelOwner);
        model.addAttribute("hotels", hotels);
        return "/hotel/hotels";
    }

    @GetMapping("/hotels/{id}")
    public String getHotelsById(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.findById(id);
        model.addAttribute("hotels", hotel);
        return "/hotel/hotels";
    }

    @GetMapping("/hotels")
    public String getAllHotels(@SortDefault("name") Pageable pageable, Model model) {
        Page<Hotel> hotels = hotelService.findAllHotels(pageable);
        model.addAttribute("hotels", hotels);
        return "/hotel/hotels";
    }

    @ExceptionHandler(HotelNotFoundException.class)
    public String HotelNotFoundExceptionHandler() {
        return "/hotel/hotel-searchException";
    }
}

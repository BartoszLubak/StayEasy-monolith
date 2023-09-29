package com.example.stayeasymonolith.controller;

import com.example.stayeasymonolith.exceptions.ExtraNotFoundException;
import com.example.stayeasymonolith.exceptions.RoomNotFoundException;
import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.service.ExtraService;
import com.example.stayeasymonolith.service.HotelService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ExtraController {
    private final ExtraService extraService;
    private final HotelService hotelService;

    public ExtraController(ExtraService extraService, HotelService hotelService) {
        this.extraService = extraService;
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels/{hotelId}/extras")
    public String getHotelExtras(@PathVariable long hotelId, Model model, Pageable pageable) throws ExtraNotFoundException {
        Hotel hotel = hotelService.findById(hotelId);
        model.addAttribute("extras", extraService.findExtrasByHotel(pageable, hotel));
        return "extra/extras-hotel";
    }
}

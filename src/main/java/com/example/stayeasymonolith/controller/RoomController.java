package com.example.stayeasymonolith.controller;

import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.service.HotelService;
import com.example.stayeasymonolith.service.RoomService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RoomController {
    private final RoomService roomService;
    private final HotelService hotelService;

    public RoomController(RoomService roomService, HotelService hotelService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels/{hotelId}/rooms")
    public String findRoomsByHotel (@PathVariable long hotelId, Model model, Pageable pageable){
        Hotel hotel = hotelService.findById(hotelId);
        model.addAttribute("rooms", roomService.findRoomsByHotel(pageable, hotel));
        return "/room/rooms";
    }
}

package com.example.stayeasymonolith.controller;

import com.example.stayeasymonolith.exceptions.RoomNotFoundException;
import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.service.HotelService;
import com.example.stayeasymonolith.service.RoomService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class RoomController {
    private final RoomService roomService;
    private final HotelService hotelService;

    public RoomController(RoomService roomService, HotelService hotelService) {
        this.roomService = roomService;
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels/{hotelId}/rooms")
    public String findRoomsByHotel(@PathVariable long hotelId, Model model, Pageable pageable) {
        Hotel hotel = hotelService.findById(hotelId);
        model.addAttribute("rooms", roomService.findRoomsByHotel(pageable, hotel));
        return "/room/rooms";
    }

    @GetMapping("/hotels/{hotelId}/available-rooms{checkInDate}{checkOutDate}")
    public String findAvailableRoomsBetweenDatesByHotel(@PathVariable long hotelId,
                                                        @RequestParam("checkInDate") LocalDate checkInDate,
                                                        @RequestParam("checkOutDate") LocalDate checkOutDate,
                                                        Model model,
                                                        Pageable pageable) {
        Hotel hotel = hotelService.findById(hotelId);
        model.addAttribute("hotel", hotel);
        model.addAttribute("checkInDate", checkInDate);
        model.addAttribute("checkOutDate", checkOutDate);
        model.addAttribute("rooms",
                roomService.findAvailableHotelRoomsBetweenDates(pageable, hotel, checkInDate, checkOutDate));
        return "/room/rooms";
    }

    @ExceptionHandler (RoomNotFoundException.class)
    public String roomNotFoundExceptionHandler() {
        return "/room/roomNotFoundException";
    }

    @ExceptionHandler (IllegalStateException.class)
    public String validDateExceptionHandler() {
        return "/room/invalidDateException";
    }
}

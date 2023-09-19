package com.example.stayeasymonolith.controller;

import com.example.stayeasymonolith.model.Reservation;
import com.example.stayeasymonolith.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/rooms/checkAvailability")
    public boolean checkRoomAvailability(@RequestBody Reservation checkRoomReservation){
        return reservationService.checkRoomAvailabilityBetweenDates(
                checkRoomReservation.getRooms().get(0),
                checkRoomReservation.getCheckIn(),
                checkRoomReservation.getCheckOut());
    }
}

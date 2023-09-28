package com.example.stayeasymonolith.exceptions;

import com.example.stayeasymonolith.controller.ReservationController;
import com.example.stayeasymonolith.controller.RoomController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = {RoomController.class, ReservationController.class})
public class ControllerExceptionHandler {

    @ExceptionHandler(RoomNotFoundException.class)
    public String handleRoomNotFoundException(Model model) {
        model.addAttribute("reservationController", "No room available between dates");
        return "/room/invalidDateException";
    }

    @ExceptionHandler (IllegalStateException.class)
    public String validDateExceptionHandler(Model model) {
        model.addAttribute("roomController", "Please check your dates");
        return "/room/invalidDateException";
    }
}

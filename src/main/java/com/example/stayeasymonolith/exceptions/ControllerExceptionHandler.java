package com.example.stayeasymonolith.exceptions;

import com.example.stayeasymonolith.controller.ExtraController;
import com.example.stayeasymonolith.controller.ReservationController;
import com.example.stayeasymonolith.controller.RoomController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = {RoomController.class, ReservationController.class, ExtraController.class})
public class ControllerExceptionHandler {

    public static final String NOT_FOUND_EXCEPTION_VIEW = "/exception/notFoundException";

    @ExceptionHandler(RoomNotFoundException.class)
    public String handleRoomNotFoundException(Model model, RoomNotFoundException e) {
        model.addAttribute("controllerException", e.getMessage());
        return NOT_FOUND_EXCEPTION_VIEW;
    }

    @ExceptionHandler (IllegalStateException.class)
    public String validDateExceptionHandler(Model model) {
        model.addAttribute("controllerException", "Please check your dates");
        return NOT_FOUND_EXCEPTION_VIEW;
    }

    @ExceptionHandler(ExtraNotFoundException.class)
    public String handleExtraNotFoundException(Model model, ExtraNotFoundException e) {
        model.addAttribute("controllerException", e.getMessage());
        return NOT_FOUND_EXCEPTION_VIEW;
    }
}
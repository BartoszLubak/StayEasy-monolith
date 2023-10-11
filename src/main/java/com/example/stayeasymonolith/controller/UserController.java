package com.example.stayeasymonolith.controller;

import com.example.stayeasymonolith.model.Reservation;
import com.example.stayeasymonolith.model.User;
import com.example.stayeasymonolith.service.ReservationService;
import com.example.stayeasymonolith.service.UserService;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ReservationService reservationService;

    public UserController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public String getUserReservations(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("userReservations", reservationService.findReservationByUser(Pageable.unpaged(), user));
        return "reservation/user-reservations";
    }
}

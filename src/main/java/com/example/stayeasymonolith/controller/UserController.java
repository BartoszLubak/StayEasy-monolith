package com.example.stayeasymonolith.controller;

import com.example.stayeasymonolith.model.User;
import com.example.stayeasymonolith.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/reservations")   //todo secured endpoint
    public String getUserReservations(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
//        model.addAttribute("reservation", );
        return "reservation/reservations";
    }

    @GetMapping("")
    public String getCurrentUser(Model model){
        model.addAttribute("userName", userService.getCurrentUser().getEmailAddress());
        return "user.userName";
    }
}

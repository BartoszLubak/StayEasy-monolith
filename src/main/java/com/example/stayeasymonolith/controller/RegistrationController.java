package com.example.stayeasymonolith.controller;

import com.example.stayeasymonolith.model.User;
import com.example.stayeasymonolith.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public String register(@ModelAttribute User user) {
        userService.singUpUser(user);
        return "redirect:/";
    }
}

package com.example.stayeasymonolith.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    private final UserService userService;

    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    void testLoadUserByUsername() {
        UserDetails user = userService.loadUserByUsername("user");
        assertEquals("user", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("Regular", user.getAuthorities().iterator().next().getAuthority());
    }
}
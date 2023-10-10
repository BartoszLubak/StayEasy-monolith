package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.model.Name;
import com.example.stayeasymonolith.model.User;
import com.example.stayeasymonolith.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    User user = new User(new Name(), "test@o2.pl", "password", false, true, List.of(), List.of());


    @Test
    void loadUserShouldReturnUser() {
        when(userRepository.findUserByEmailAddress("user@test.pl")).thenReturn(Optional.of(user));

        UserDetails currentUser = userService.loadUserByUsername("user@test.pl");

        assertEquals(currentUser.getUsername(), user.getEmailAddress());
        assertEquals(currentUser.getPassword(), user.getPassword());
    }

    @Test
    void loadUserShouldThrowException() {
        assertThrows(UsernameNotFoundException.class, ()-> userService.loadUserByUsername("wrong@mail.pl"));
    }

    @Test
    void getCurrentUserShouldReturnUser() {
        when(userRepository.findUserByEmailAddress("test@o2.pl")).thenReturn(Optional.of(user));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities()));

        assertEquals(user, userService.getCurrentUser());
    }

    @Test
    void getCurrentUserShouldThrowException() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, "password", user.getAuthorities()));

        assertThrows(UsernameNotFoundException.class, ()-> userService.getCurrentUser());
    }
}
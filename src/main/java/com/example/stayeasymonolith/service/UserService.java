package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.model.User;
import com.example.stayeasymonolith.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Transactional
    public void singUpUser(User user) {
        if (userRepository.findUserByEmailAddress(user.getEmailAddress()).isPresent()) {
            throw new IllegalStateException("Address email already exists");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        log.info("User saved in database");
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmailAddress(emailAddress)
                .orElseThrow(() -> new UsernameNotFoundException("User with email address: %s not found"
                        .formatted(emailAddress)));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmailAddress())
                .password(user.getPassword())
                .roles(user.getName().getType().toString())
                .accountExpired(!user.isAccountNonExpired())
                .accountLocked(!user.isAccountNonLocked())
                .credentialsExpired(!user.isCredentialsNonExpired())
                .disabled(!user.isEnabled())
                .build();
    }

    public User getCurrentUser() throws UsernameNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principalName = authentication.getName();
        if (principalName.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        return userRepository
                .findUserByEmailAddress(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
        log.info("Saved user: {}", user);
    }
}

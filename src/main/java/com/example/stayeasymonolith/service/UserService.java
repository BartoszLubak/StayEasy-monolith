package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.configuration.PasswordEncoder;
import com.example.stayeasymonolith.model.User;
import com.example.stayeasymonolith.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void singUpUser(User user) {
        if (userRepository.findUserByEmailAddress(user.getEmailAddress()).isPresent()) {
            throw new IllegalStateException("Address Email Already exists");
        }
        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmailAddress(emailAddress)
                .orElseThrow(()->new UsernameNotFoundException("User with emial address: %s not found".formatted(emailAddress)));
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
}

package com.example.stayeasymonolith.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Name name;
    @Column(name = "email")
    private String emailAddress;
    private String password;
    private Boolean locked = false;
    private Boolean enabled = true;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "user")
    private List<Hotel> hotels;

    public User(String firstName, String emailAddress, String password, Type type) {
        this.name = new Name(firstName);
        this.emailAddress = emailAddress;
        this.password = password;
        this.name.setType(type);
    }

    public User(Name name, String emailAddress, String password, Boolean locked, Boolean enabled, List<Reservation> reservations, List<Hotel> hotels) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.password = password;
        this.locked = locked;
        this.enabled = enabled;
        this.reservations = reservations;
        this.hotels = hotels;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(name.getType().toString());
        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

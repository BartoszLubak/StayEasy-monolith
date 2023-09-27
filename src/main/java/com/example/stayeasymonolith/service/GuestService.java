package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.model.Guest;
import com.example.stayeasymonolith.model.Reservation;
import com.example.stayeasymonolith.repository.GuestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public void setReservation(Reservation reservation, List<Guest> guests) {
        for (Guest guest : guests) {
            guest.setReservation(reservation);
        }
    }

    @Transactional
    public void saveGuests(List<Guest> guests) {
        guestRepository.saveAll(guests);
    }
}

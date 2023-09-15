package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.model.Guest;
import com.example.stayeasymonolith.model.Reservation;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.repository.ReservationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Page<Reservation> findReservationsByGuestContaining(Pageable pageable, Guest guest) {
        Page<Reservation> reservationsByGuest = reservationRepository.findReservationsByGuestContaining(pageable, guest);
        return reservationsByGuest;
    }

    public Page<Reservation> findReservationsByRoomContaining(Pageable pageable, Room room) {
        Page<Reservation> reservationsByRoom = reservationRepository.findReservationsByRoomsContaining(pageable, room);
        return reservationsByRoom;
    }

    public boolean checkRoomAvailabilityBetweenDates(Room room, LocalDate guestCheckIn, LocalDate guestCheckOut) {
        List<Reservation> reservations = findReservationsByRoomContaining(Pageable.unpaged(), room)
                .stream()
                .filter(reservation -> reservation.getCheckIn().isBefore(guestCheckOut) &&
                                       reservation.getCheckOut().isAfter(guestCheckIn))
                .toList();
        return reservations.isEmpty();
    }
}

package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.model.Guest;
import com.example.stayeasymonolith.model.Reservation;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public void addAvailableRoomToReservation(Reservation reservation, List<Room> availableRooms) throws IllegalArgumentException {
        Random random = new Random();
        Room selectedRoom = availableRooms.get(random.nextInt(availableRooms.size()));
        reservation.setRooms(List.of(selectedRoom));
        selectedRoom.getReservations().add(reservation);
    }

    public void addGuestToReservation(Guest guest, Reservation reservation) {
        List<Guest> guests = reservation.getGuest();
        if (guests == null) {
            guests = new ArrayList<>();
        }
        for(Guest guestAdd:guests){
            guestAdd.setReservation(reservation);
        }
        guests.add(guest);
        reservation.setGuest(guests);
    }

    public boolean checkRoomCapacity(Reservation reservation, Room room) {
        List<Guest> guests = reservation.getGuest();
        return guests != null && guests.size() >= room.getRoomCapacity();
    }

    public BigDecimal updateReservationCostsWithoutExtras(Reservation reservation) {
        BigDecimal reservationRoomCost = getReservationRoomCostWithoutExtras(reservation);
        reservation.setReservationCost(reservationRoomCost);
        return reservationRoomCost;
    }

    public BigDecimal getReservationRoomCostWithoutExtras(Reservation reservation) {
        BigDecimal roomCost = reservation.getRooms().get(0).getCost();
        long nights = ChronoUnit.DAYS.between(reservation.getCheckIn(), reservation.getCheckOut());
        return roomCost.multiply(BigDecimal.valueOf(nights));
    }
    @Transactional
    public void save(Reservation reservation){
        reservationRepository.save(reservation);
    }
}

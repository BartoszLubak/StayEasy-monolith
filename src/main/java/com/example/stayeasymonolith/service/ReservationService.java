package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.exceptions.ReservationNotFoundException;
import com.example.stayeasymonolith.model.*;
import com.example.stayeasymonolith.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        for (Guest guestAdd : guests) {
            guestAdd.setReservation(reservation);
        }
        guests.add(guest);
        reservation.setGuest(guests);
    }

    public boolean checkRoomCapacity(Reservation reservation, Room room) {
        List<Guest> guests = reservation.getGuest();
        return guests != null && guests.size() >= room.getRoomCapacity();
    }

    public void updateReservationCostsWithoutExtras(Reservation reservation) {
        BigDecimal reservationRoomCost = getReservationRoomCostWithoutExtras(reservation);
        reservation.setReservationCost(reservationRoomCost);
    }

    public BigDecimal getReservationRoomCostWithoutExtras(Reservation reservation) {
        BigDecimal roomCost = reservation.getRooms().get(0).getCost();
        long nights = ChronoUnit.DAYS.between(reservation.getCheckIn(), reservation.getCheckOut());
        return roomCost.multiply(BigDecimal.valueOf(nights));
    }

    public void addExtrasCostsToReservation(Reservation reservation, BigDecimal allExtrasCost) {
        reservation.setReservationCost(reservation.getReservationCost().add(allExtrasCost));
        log.info("Extras costs: {} added to reservation", allExtrasCost);
    }

    public void addExtrasToReservation(Reservation reservation, List<Extra> selectedExtras) {
        reservation.setExtras(selectedExtras);
        log.info("Extras added {} to reservation", selectedExtras);
    }

    public Page<Reservation> findReservationByUser(Pageable pageable, User user) {
        Page<Reservation> reservationsByUserContains = reservationRepository.findAllByUserContains(pageable, user.getEmailAddress());
        if (reservationsByUserContains.isEmpty()) {
            log.info("User {} do not have reservations", user.getEmailAddress());
            throw new ReservationNotFoundException("User: " + user.getEmailAddress() + " has no reservations");
        } else {
        return reservationsByUserContains;
        }
    }

    @Transactional
    public Reservation save(Reservation reservation) {
        reservation = reservationRepository.save(reservation);
        log.info("Saved reservation: {}", reservation);
        return reservation;
    }
}

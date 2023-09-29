package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.exceptions.RoomNotFoundException;
import com.example.stayeasymonolith.model.*;
import com.example.stayeasymonolith.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final ReservationService reservationService;
    private final MessageSource messageSource;

    public RoomService(RoomRepository roomRepository, ReservationService reservationServiceService, MessageSource messageSource) {
        this.roomRepository = roomRepository;
        this.reservationService = reservationServiceService;
        this.messageSource = messageSource;
    }

    public Room findById(long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room with given id: %s do not exist".formatted(id)));
    }

    public Page<Room> findRoomsByHotel(Pageable pageable, Hotel hotel) {
        Page<Room> rooms = roomRepository.findRoomsByHotel(pageable, hotel);
        throwRoomNotFoundExceptionWhenRoomPageIsEmpty(rooms);
        return rooms;
    }

    public Page<Room> findRoomsByHotelAndRoomType(Pageable pageable, Hotel hotel, RoomType roomType) {
        Page<Room> rooms = roomRepository.findRoomsByHotelAndRoomType(pageable, hotel, roomType);
        throwRoomNotFoundExceptionWhenRoomPageIsEmpty(rooms);
        return rooms;
    }

    public Page<Room> findRoomsByHotelAndRoomTypeAndCostBetween(Pageable pageable,
                                                                Hotel hotel,
                                                                RoomType roomType,
                                                                BigDecimal minCost,
                                                                BigDecimal maxCost) {
        Page<Room> availableRooms;
        if (roomType == null) {
            availableRooms = roomRepository
                    .findRoomsByHotelAndCostBetween(pageable, hotel, minCost, maxCost);
        } else {
            availableRooms = roomRepository
                    .findRoomsByHotelAndRoomTypeAndCostBetween(pageable, hotel, roomType, minCost, maxCost);
        }
        throwRoomNotFoundExceptionWhenRoomPageIsEmpty(availableRooms);
        return availableRooms;
    }

    public List<Room> findAvailableHotelRoomsBetweenDates(Pageable pageable,
                                                          Hotel hotel,
                                                          Reservation reservation) {
        LocalDate today = LocalDate.now();
        reservation.setCheckIn((reservation.getCheckIn() != null && !reservation.getCheckIn().isBefore(today)) ? reservation.getCheckIn() : today);
        LocalDate finalCheckIn = reservation.getCheckIn();
        LocalDate checkOut = reservation.getCheckOut();
        if (checkOut.isBefore(finalCheckIn)) {
            throw new IllegalStateException("Invalid dates");
        }
        return findRoomsByHotel(pageable, hotel)
                .stream()
                .filter(room -> reservationService.checkRoomAvailabilityBetweenDates(room, finalCheckIn, checkOut))
                .toList();
    }

    private void throwRoomNotFoundExceptionWhenRoomPageIsEmpty(Page<Room> rooms) {
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException("No available rooms");
        }
    }

    public List<String> getRoomTypeEnumNamesFromProperties(Set<RoomType> roomTypes) {
        return roomTypes.stream()
                .map(roomType -> messageSource.getMessage("enum.RoomType." + roomType, null, LocaleContextHolder.getLocale()))
                .toList();
    }

    public void setReservation(Reservation reservation, List<Room> rooms) {
        for (Room room : rooms) {
            room.getReservations().add(reservation);
        }
    }

    @Transactional
    public void saveRooms(List<Room> rooms) {
        roomRepository.saveAll(rooms);
    }
}

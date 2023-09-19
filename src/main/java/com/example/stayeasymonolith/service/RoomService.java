package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.exceptions.RoomNotFoundException;
import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.model.RoomType;
import com.example.stayeasymonolith.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final ReservationService reservationServiceService;

    public RoomService(RoomRepository roomRepository, ReservationService reservationServiceService) {
        this.roomRepository = roomRepository;
        this.reservationServiceService = reservationServiceService;
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
        Page<Room> availableRooms = null;
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

    public List<Room> findAvailableHotelRoomBetweenDates(Pageable pageable,
                                                         Hotel hotel,
                                                         LocalDate checkIn,
                                                         LocalDate checkOut) {
        return findRoomsByHotel(pageable, hotel)
                .stream()
                .filter(room -> reservationServiceService.checkRoomAvailabilityBetweenDates(room, checkIn, checkOut))
                .collect(Collectors.toList());
    }

    private void throwRoomNotFoundExceptionWhenRoomPageIsEmpty(Page<Room> rooms) {
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException("Room List is empty.");
        }
    }
}

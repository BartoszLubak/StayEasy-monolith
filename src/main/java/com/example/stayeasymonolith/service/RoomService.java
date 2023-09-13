package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.exceptions.RoomNotFoundException;
import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.model.RoomType;
import com.example.stayeasymonolith.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Page<Room> findRoomsByHotel(Pageable pageable, Hotel hotel) {
        Page<Room> rooms = roomRepository.findRoomsByHotel(pageable, hotel);
        throwRoomNotFoundExceptionWhenRoomPageIsEmpty(rooms);
        return rooms;
    }


    public Page<Room> findAvailableRoomsByHotel(Pageable pageable, Hotel hotel, boolean availability) {
        Page<Room> rooms = roomRepository.findRoomsByHotelAndAvailability(pageable, hotel, availability);
        throwRoomNotFoundExceptionWhenRoomPageIsEmpty(rooms);
        return rooms;
    }

    public Page<Room> findAvailableRoomsByHotelAndRoomType(Pageable pageable, Hotel hotel, boolean availability, RoomType roomType) {
        Page<Room> rooms = roomRepository.findRoomsByHotelAndAvailabilityAndRoomType(pageable, hotel, availability, roomType);
        throwRoomNotFoundExceptionWhenRoomPageIsEmpty(rooms);
        return rooms;
    }

    public Page<Room> findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetween(Pageable pageable,
                                                                               Hotel hotel,
                                                                               boolean availability,
                                                                               RoomType roomType,
                                                                               BigDecimal minCost,
                                                                               BigDecimal maxCost) {
        Page<Room> availableRooms = null;
        if (roomType == null) {
            availableRooms = roomRepository
                    .findRoomsByHotelAndAvailabilityAndCostBetween(pageable, hotel, availability, minCost, maxCost);
        } else {
            availableRooms = roomRepository
                    .findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetween(pageable, hotel, availability, roomType, minCost, maxCost);
        }
        throwRoomNotFoundExceptionWhenRoomPageIsEmpty(availableRooms);
        return availableRooms;
    }

    private void throwRoomNotFoundExceptionWhenRoomPageIsEmpty(Page<Room> rooms) {
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException("Room List is empty.");
        }
    }
}

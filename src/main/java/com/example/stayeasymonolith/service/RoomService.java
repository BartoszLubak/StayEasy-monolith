package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.model.RoomType;
import com.example.stayeasymonolith.repository.RoomRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Page<Room> findRoomsByHotel(Hotel hotel) {
        return roomRepository.findRoomsByHotel(hotel);
    }

    public Page<Room> findAvailableRoomsByHotel(Hotel hotel, boolean availability) {
        return roomRepository.findRoomsByHotelAndAvailability(hotel, availability);
    }

    public Page<Room> findAvailableRoomsByHotelAndRoomType(Hotel hotel, boolean availability, RoomType roomType) {
        return roomRepository.findRoomsByHotelAndAvailabilityAndRoomType(hotel, availability, roomType);
    }

    public Page<Room> findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetween(Hotel hotel,
                                                                               boolean availability,
                                                                               RoomType roomType,
                                                                               BigDecimal minCost,
                                                                               BigDecimal maxCost) {
        Page<Room> availableRooms = null;
        if (roomType == null) {
            availableRooms = roomRepository
                    .findRoomsByHotelAndAvailabilityAndCostBetween(hotel, availability, minCost, maxCost);
        } else {
            availableRooms = roomRepository
                    .findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetween(hotel, availability, roomType, minCost, maxCost);
        }
        return availableRooms;
    }
}

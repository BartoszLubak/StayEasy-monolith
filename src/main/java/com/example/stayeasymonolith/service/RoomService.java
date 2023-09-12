package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

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
}

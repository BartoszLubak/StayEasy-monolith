package com.example.stayeasymonolith.repository;

import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Page<Room> findRoomsByHotel(Hotel hotel);

    Page<Room> findRoomsByHotelAndAvailability(Hotel hotel, boolean availability);
}

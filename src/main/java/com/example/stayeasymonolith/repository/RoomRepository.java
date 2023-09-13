package com.example.stayeasymonolith.repository;

import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.model.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Page<Room> findRoomsByHotel(Hotel hotel);

    Page<Room> findRoomsByHotelAndAvailability(Hotel hotel, boolean availability);

    Page<Room> findRoomsByHotelAndAvailabilityAndRoomType(Hotel hotel,
                                                          boolean availability,
                                                          RoomType roomType);

    Page<Room> findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetween (Hotel hotel,
                                                                         boolean availability,
                                                                         RoomType roomType,
                                                                         BigDecimal minCost,
                                                                         BigDecimal maxCost);

    Page<Room> findRoomsByHotelAndAvailabilityAndCostBetween(Hotel hotel,
                                                             boolean availability,
                                                             BigDecimal minCost,
                                                             BigDecimal maxCost);
}

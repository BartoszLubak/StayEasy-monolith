package com.example.stayeasymonolith.repository;

import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.model.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Page<Room> findRoomsByHotel(Pageable pageable, Hotel hotel);

    Page<Room> findRoomsByHotelAndAvailability(Pageable pageable, Hotel hotel, boolean availability);

    Page<Room> findRoomsByHotelAndAvailabilityAndRoomType(Pageable pageable,
                                                          Hotel hotel,
                                                          boolean availability,
                                                          RoomType roomType);

    Page<Room> findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetween (Pageable pageable,
                                                                         Hotel hotel,
                                                                         boolean availability,
                                                                         RoomType roomType,
                                                                         BigDecimal minCost,
                                                                         BigDecimal maxCost);

    Page<Room> findRoomsByHotelAndAvailabilityAndCostBetween(Pageable pageable,
                                                             Hotel hotel,
                                                             boolean availability,
                                                             BigDecimal minCost,
                                                             BigDecimal maxCost);
}

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

    Page<Room> findRoomsByHotelAndRoomType(Pageable pageable,
                                           Hotel hotel,
                                           RoomType roomType);

    Page<Room> findRoomsByHotelAndRoomTypeAndCostBetween(Pageable pageable,
                                                         Hotel hotel,
                                                         RoomType roomType,
                                                         BigDecimal minCost,
                                                         BigDecimal maxCost);

    Page<Room> findRoomsByHotelAndCostBetween(Pageable pageable,
                                              Hotel hotel,
                                              BigDecimal minCost,
                                              BigDecimal maxCost);
}

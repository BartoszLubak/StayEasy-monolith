package com.example.stayeasymonolith.repository;

import com.example.stayeasymonolith.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository <Hotel, Long> {
    Page<Hotel> findHotelsByAddress_City(Pageable pageable, String city);
}

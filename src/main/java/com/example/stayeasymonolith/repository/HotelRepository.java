package com.example.stayeasymonolith.repository;

import com.example.stayeasymonolith.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findHotelById(long id);

    Page<Hotel> findHotelsByName(Pageable pageable, String name);

    Page<Hotel> findHotelsByAddress_City(Pageable pageable, String city);
    Page<Hotel> findHotelsByHotelOwner(Pageable pageable, String hotelOwner);

    @Query("SELECT h FROM Hotel h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%'))"
            + "AND LOWER(h.address.city) LIKE LOWER(CONCAT('%', :city, '%'))")
    Page<Hotel> findAllByNameAndAddress_City(Pageable pageable,
                                             @Param("name") String name,
                                             @Param("city") String city);

    @Query("SELECT DISTINCT h FROM Hotel h JOIN h.rooms r WHERE r.cost BETWEEN :minPrice AND :maxPrice")
    Page<Hotel> findHotelsByRoomPriceRange(Pageable pageable,
                                           @Param("minPrice") BigDecimal minPrice,
                                           @Param("maxPrice") BigDecimal maxPrice);
}

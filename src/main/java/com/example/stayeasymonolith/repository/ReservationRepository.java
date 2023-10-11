package com.example.stayeasymonolith.repository;

import com.example.stayeasymonolith.model.Guest;
import com.example.stayeasymonolith.model.Reservation;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findReservationsByGuestContaining(Pageable pageable, Guest guest);

    Page<Reservation> findReservationsByRoomsContaining(Pageable pageable, Room room);

    @Query("SELECT r FROM Reservation r WHERE r.user.emailAddress LIKE :userEmailAddress")
    Page<Reservation> findAllByUserContains(Pageable pageable,
                                            @Param("userEmailAddress") String userEmailAddress);
}

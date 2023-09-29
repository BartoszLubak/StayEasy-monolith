package com.example.stayeasymonolith.repository;

import com.example.stayeasymonolith.model.Extra;
import com.example.stayeasymonolith.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtraRepository extends JpaRepository<Extra, Long> {
    List<Extra> findExtraByRoomListIsContaining(Room room);

    Page<Extra> findExtrasByRoomListIn(Pageable pageable, List<Room> rooms);
}

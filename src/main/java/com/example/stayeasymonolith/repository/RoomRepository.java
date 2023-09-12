package com.example.stayeasymonolith.repository;

import com.example.stayeasymonolith.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}

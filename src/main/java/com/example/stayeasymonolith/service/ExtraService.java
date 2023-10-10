package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.exceptions.ExtraNotFoundException;
import com.example.stayeasymonolith.exceptions.RoomNotFoundException;
import com.example.stayeasymonolith.model.Extra;
import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.model.Reservation;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.repository.ExtraRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ExtraService {
    private final ExtraRepository extraRepository;
    private final RoomService roomService;

    public ExtraService(ExtraRepository extraRepository, RoomService roomService) {
        this.extraRepository = extraRepository;
        this.roomService = roomService;
    }

    public BigDecimal getAllExtrasCost(List<Extra> selectedExtras) {
        return selectedExtras.stream().map(Extra::getCost).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Extra> findExtrasByRoom(Room room) {
        return extraRepository.findExtraByRoomListIsContaining(room);
    }

    public List<Extra> extrasByIds(List<Long> extrasIds) {
        return extraRepository.findAllById(extrasIds);
    }

    public Page<Extra> findExtrasByHotel(Pageable pageable, Hotel hotel) throws ExtraNotFoundException {
        try {
            Page<Room> rooms = roomService.findRoomsByHotel(Pageable.unpaged(), hotel);
            if (rooms.isEmpty()) {
                throw new RoomNotFoundException("No rooms found for the hotel");
            }
            return extraRepository.findExtrasByRoomListIn(pageable, rooms.getContent());
        } catch (RoomNotFoundException e) {
            e.printStackTrace();
            throw new ExtraNotFoundException("No extras available");
        }
    }

    public void setReservation(Reservation reservation, List<Extra> extras) {
        for (Extra extra : extras) {
            extra.getReservations().add(reservation);
        }
    }

    @Transactional
    public void saveExtras(List<Extra> extras) {
        extraRepository.saveAll(extras);
        log.info("Saved extras: {}", extras);
    }


}

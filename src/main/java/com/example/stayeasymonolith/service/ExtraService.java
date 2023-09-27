package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.model.Extra;
import com.example.stayeasymonolith.model.Guest;
import com.example.stayeasymonolith.model.Reservation;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.repository.ExtraRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExtraService {
    private final ExtraRepository extraRepository;

    public ExtraService(ExtraRepository extraRepository) {
        this.extraRepository = extraRepository;
    }

    public BigDecimal getAllExtrasCost(List<Extra> selectedExtras) {
        return selectedExtras.stream().map(Extra::getCost).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Extra> findExtrasByRoom(Room room) {
        return extraRepository.findExtraByRoomListIsContaining(room);
    }

    public Extra getExtraById(long extraId) {
        return extraRepository.findExtrasById(extraId);
    }

    public List<Extra> extrasByIds(List<Long> extrasIds) {
        return extraRepository.findAllById(extrasIds);
    }

    public void setReservation(Reservation reservation, List<Extra> extras) {
        for (Extra extra : extras) {
            extra.getReservations().add(reservation);
        }
    }
    @Transactional
    public void saveExtras(List<Extra> extras) {
        extraRepository.saveAll(extras);
    }
}

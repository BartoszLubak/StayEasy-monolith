package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.model.Extra;
import com.example.stayeasymonolith.model.Reservation;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.repository.ExtraRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExtraServiceTest {
    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    @Mock
    private ExtraRepository extraRepository;
    @InjectMocks
    private ExtraService extraService;

    Extra extra1 = new Extra(1L, "breakfast", BigDecimal.valueOf(50), new ArrayList<>(), new ArrayList<>());
    Extra extra2 = new Extra(2L, "late checkout", BigDecimal.valueOf(20), new ArrayList<>(), new ArrayList<>());
    Extra extra3 = new Extra(3L, "SPA", BigDecimal.valueOf(100), new ArrayList<>(), new ArrayList<>());

    @Test
    void getAllExtrasCostShouldReturnExtrasCostSum() {
        List<Extra> selectedExtras = List.of(extra1, extra2, extra3);
        BigDecimal totalCost = extra1.getCost().add(extra2.getCost()).add(extra3.getCost());

        BigDecimal totalExtraCost = extraService.getAllExtrasCost(selectedExtras);

        assertThat(totalExtraCost).isEqualTo(totalCost);
    }

    @Test
    void findExtrasByRoomShouldReturnExtrasList() {
        Room room = new Room();
        when(extraRepository.findExtraByRoomListIsContaining(room)).thenReturn(List.of(extra1, extra3));

        assertThat(extraService.findExtrasByRoom(room))
                .isNotNull()
                .hasSize(2)
                .extracting(DESCRIPTION)
                .containsExactly("breakfast", "SPA");
    }

    @Test
    void extrasByIdsShouldReturnExtrasList() {
        when(extraRepository.findAllById(List.of(1L, 3L))).thenReturn(List.of(extra1, extra3));

        assertThat(extraService.extrasByIds(List.of(1L, 3L)))
                .hasSize(2)
                .extracting(ID)
                .containsExactly(1L, 3L);
    }

    @Test
    void setReservation() {
        Reservation reservation = new Reservation();
        List<Extra> extras = Arrays.asList(extra1, extra2, extra3);

        extraService.setReservation(reservation, extras);

        assertThat(extra1.getReservations()).contains(reservation);
        assertThat(extra2.getReservations()).contains(reservation);
        assertThat(extra3.getReservations()).contains(reservation);
    }
}
package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.model.Guest;
import com.example.stayeasymonolith.model.Reservation;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ReservationService reservationService;
    Room room1 = new Room();
    Room room2 = new Room();
    Room room3 = new Room();
    Guest guest1 = new Guest();
    Guest guest2 = new Guest();
    Reservation reservation1 = new Reservation(1L,
            List.of(room1),
            LocalDate.of(2023, 11, 2),
            LocalDate.of(2023, 11, 5),
            List.of(guest1));
    Reservation reservation2 = new Reservation(2L,
            List.of(room1, room2),
            LocalDate.of(2024, 1, 22),
            LocalDate.of(2024, 1, 31),
            List.of(guest1, guest2));
    Reservation reservation3 = new Reservation(3L,
            List.of(room1, room2, room3),
            LocalDate.of(2023, 11, 5),
            LocalDate.of(2023, 11, 7),
            List.of(guest2));
    private Page<Reservation> getReservationsByRoomsContaining(Room room) {
        return reservationRepository.findReservationsByRoomsContaining(Pageable.unpaged(), room);
    }

    @Test
    void findReservationsByGuestShouldReturnCorrectReservations() {
        when(reservationRepository.findReservationsByGuestContaining(Pageable.unpaged(), guest1))
                .thenReturn(new PageImpl<>(List.of(reservation1, reservation2)));

        Page<Reservation> result = reservationService.findReservationsByGuestContaining(Pageable.unpaged(), guest1);

        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .extracting("guest")
                .containsExactly(List.of(guest1), List.of(guest1, guest2));
    }

    @Test
    void findReservationsByRoomContaining() {
        when(getReservationsByRoomsContaining(room2)).thenReturn(new PageImpl<>(List.of(reservation2, reservation3)));

        Page<Reservation> result = reservationService.findReservationsByRoomContaining(Pageable.unpaged(), room2);

        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .extracting("rooms")
                .containsExactly(List.of(room1, room2), List.of(room1, room2, room3));
    }



    @Test
    void checkRoomAvailabilityBetweenDatesWhenReservationCheckOutEqualsGuestCheckIn() {
        LocalDate guestCheckIn = LocalDate.of(2023, 11, 7);
        LocalDate guestCheckOut = LocalDate.of(2023, 11, 11);
        when(getReservationsByRoomsContaining(room1)).thenReturn(new PageImpl<>(List.of(reservation1, reservation3)));

        boolean isRoomAvailable = reservationService.checkRoomAvailabilityBetweenDates(room1, guestCheckIn, guestCheckOut);

        assertThat(isRoomAvailable).isTrue();
    }
    @Test
    void checkRoomAvailabilityBetweenDatesWhenReservationCheckInEqualsGuestCheckOu() {
        LocalDate guestCheckIn = LocalDate.of(2023, 11, 1);
        LocalDate guestCheckOut = LocalDate.of(2023, 11, 2);
        when(getReservationsByRoomsContaining(room1)).thenReturn(new PageImpl<>(List.of(reservation1, reservation3)));

        boolean isRoomAvailable = reservationService.checkRoomAvailabilityBetweenDates(room1, guestCheckIn, guestCheckOut);

        assertThat(isRoomAvailable).isTrue();
    }
}
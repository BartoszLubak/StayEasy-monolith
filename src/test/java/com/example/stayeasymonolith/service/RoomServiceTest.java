package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.exceptions.RoomNotFoundException;
import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.model.Reservation;
import com.example.stayeasymonolith.model.Room;
import com.example.stayeasymonolith.model.RoomType;
import com.example.stayeasymonolith.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private RoomService roomService;
    Hotel hotel = new Hotel();
    Room room1 = new Room(0L, 1, hotel, RoomType.FAMILY, 3, List.of(new Reservation()), List.of(), BigDecimal.valueOf(200));
    Room room2 = new Room(1L, 2, hotel, RoomType.PREMIUM, 2, List.of(new Reservation()), List.of(), BigDecimal.valueOf(350));
    Room room3 = new Room(2L, 3, hotel, RoomType.EXTRA_VIEW, 2, List.of(new Reservation()), List.of(), BigDecimal.valueOf(300));
    Room room4 = new Room(2L, 4, hotel, RoomType.EXTRA_VIEW, 2, List.of(new Reservation()), List.of(), BigDecimal.valueOf(300));

    List<Room> roomsAtHotel = List.of(room1, room2, room3, room4);
    BigDecimal minCost = BigDecimal.valueOf(280);
    BigDecimal maxCost = BigDecimal.valueOf(380);

    @Test
    void findRoomsByHotelShouldReturnCorrectPage() {
        when(roomRepository
                .findRoomsByHotel(Pageable.unpaged(), hotel))
                .thenReturn(new PageImpl<>(roomsAtHotel));

        assertThat(roomService.findRoomsByHotel(Pageable.unpaged(), hotel))
                .isNotNull()
                .hasSize(4)
                .extracting("roomNumber")
                .containsExactly(1, 2, 3, 4);
    }

    @Test
    void findRoomsByHotelAndRoomTypeShouldReturnRoomsWithCorrectType() {
        when(roomRepository
                .findRoomsByHotelAndRoomType(Pageable.unpaged(), hotel, RoomType.EXTRA_VIEW))
                .thenReturn(new PageImpl<>(List.of(room4)));

        assertThat(roomService
                .findRoomsByHotelAndRoomType(Pageable.unpaged(), hotel, RoomType.EXTRA_VIEW))
                .isNotNull()
                .hasSize(1)
                .extracting("roomNumber")
                .containsExactly(4);

        assertThat(roomService
                .findRoomsByHotelAndRoomType(Pageable.unpaged(), hotel, RoomType.EXTRA_VIEW))
                .extracting("roomType")
                .containsExactly(RoomType.EXTRA_VIEW);
    }

    @Test
    void findRoomsByHotelAndRoomTypeAndCostBetweenShouldReturnCorrectRoomsInPriceRange() {
        PageImpl<Room> expectedRooms = new PageImpl<>(List.of(room2, room3, room4));

        when(roomRepository
                .findRoomsByHotelAndRoomTypeAndCostBetween(
                        Pageable.unpaged(),
                        hotel,
                        RoomType.EXTRA_VIEW,
                        minCost,
                        maxCost))
                .thenReturn(expectedRooms);

        Page<Room> result = roomService.findRoomsByHotelAndRoomTypeAndCostBetween(
                Pageable.unpaged(),
                hotel,
                RoomType.EXTRA_VIEW,
                minCost,
                maxCost);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(3, result.getTotalElements()),
                () -> assertTrue(result.getContent().contains(room2)),
                () -> assertTrue(result.getContent().contains(room3)),
                () -> assertTrue(result.getContent().contains(room4))
        );
    }

    @Test
    void findAvailableRoomsByHotelShouldThrowRoomNotFoundExceptionWhenAllRoomsAreUnavailable() {
        when(roomRepository
                .findRoomsByHotel(Pageable.unpaged(), hotel)).thenReturn(Page.empty());

        assertThatThrownBy(() -> roomService
                .findRoomsByHotel(Pageable.unpaged(), hotel))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessage("Room List is empty.");
    }

    @Test
    void findAvailableRoomsByHotelAndRoomTypeShouldThrowRoomNotFoundExceptionWhenAllRoomsAreUnavailable() {
        when(roomRepository
                .findRoomsByHotelAndRoomType(Pageable.unpaged(), hotel, RoomType.REGULAR))
                .thenReturn(Page.empty());

        assertThatThrownBy(() -> roomService
                .findRoomsByHotelAndRoomType(Pageable.unpaged(), hotel, RoomType.REGULAR))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessage("Room List is empty.");
    }

    @Test
    void findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetweenShouldThrowRoomNotFoundExceptionWhenAllRoomsAreUnavailable() {
        when(roomRepository
                .findRoomsByHotelAndRoomTypeAndCostBetween(Pageable.unpaged(), hotel, RoomType.REGULAR, minCost, maxCost))
                .thenReturn(Page.empty());

        assertThatThrownBy(() -> roomService
                .findRoomsByHotelAndRoomTypeAndCostBetween(Pageable.unpaged(), hotel, RoomType.REGULAR, minCost, maxCost))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessage("Room List is empty.");
    }

    @Test
    void findAvailableHotelRoomsBetweenDatesShouldThrownExceptionThenCheckInIsAfterCheckOut() {
        LocalDate checkOut = LocalDate.now();
        LocalDate checkIn = checkOut.plusDays(1);
        Reservation reservation = new Reservation();
        reservation.setCheckIn(checkIn);
        reservation.setCheckOut(checkOut);

        assertThatThrownBy(() -> roomService
                .findAvailableHotelRoomsBetweenDates(Pageable.unpaged(), hotel, reservation))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Invalid dates");
    }
}
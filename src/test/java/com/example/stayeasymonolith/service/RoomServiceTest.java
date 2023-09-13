package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.exceptions.RoomNotFoundException;
import com.example.stayeasymonolith.model.Hotel;
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
    Room room1 = new Room(0L, 1, hotel, RoomType.FAMILY, 3, 6, BigDecimal.valueOf(200), true);
    Room room2 = new Room(1L, 2, hotel, RoomType.PREMIUM, 2, 3, BigDecimal.valueOf(350), false);
    Room room3 = new Room(2L, 3, hotel, RoomType.EXTRA_VIEW, 2, 4, BigDecimal.valueOf(300), false);
    Room room4 = new Room(2L, 4, hotel, RoomType.EXTRA_VIEW, 2, 4, BigDecimal.valueOf(300), true);

    List<Room> roomsAtHotel = List.of(room1, room2, room3, room4);
    List<Room> roomsAvailable = List.of(room1, room4);
    BigDecimal minCost = BigDecimal.valueOf(280);
    BigDecimal maxCost = BigDecimal.valueOf(380);

    @Test
    void findRoomsByHotelShouldReturnCorrectPage() {
        when(roomRepository
                .findRoomsByHotel(Pageable.unpaged(), hotel))
                .thenReturn(new PageImpl<>(roomsAtHotel));

        assertThat(roomService.findRoomsByHotel(Pageable.unpaged() ,hotel))
                .isNotNull()
                .hasSize(4)
                .extracting("roomNumber")
                .containsExactly(1, 2, 3, 4);
    }

    @Test
    void findAvailableRoomsByHotelShouldReturnAvailableRooms() {
        when(roomRepository
                .findRoomsByHotelAndAvailability(Pageable.unpaged(), hotel, true))
                .thenReturn(new PageImpl<>(roomsAvailable));

        assertThat(roomService.findAvailableRoomsByHotel(Pageable.unpaged(), hotel, true))
                .isNotNull()
                .hasSize(2)
                .extracting("roomNumber")
                .containsExactly(1, 4);
    }

    @Test
    void findAvailableRoomsByHotelAndRoomTypeShouldReturnAvailableRoomsWithCorrectType() {
        when(roomRepository
                .findRoomsByHotelAndAvailabilityAndRoomType(Pageable.unpaged(), hotel, true, RoomType.EXTRA_VIEW))
                .thenReturn(new PageImpl<>(List.of(room4)));

        assertThat(roomService
                .findAvailableRoomsByHotelAndRoomType(Pageable.unpaged(), hotel, true, RoomType.EXTRA_VIEW))
                .isNotNull()
                .hasSize(1)
                .extracting("roomNumber")
                .containsExactly(4);

        assertThat(roomService
                .findAvailableRoomsByHotelAndRoomType(Pageable.unpaged(), hotel, true, RoomType.EXTRA_VIEW))
                .extracting("roomType")
                .containsExactly(RoomType.EXTRA_VIEW);
    }

    @Test
    void findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetweenShouldReturnCorrectRoomsInPriceRange() {
        PageImpl<Room> expectedRooms = new PageImpl<>(List.of(room2, room3, room4));

        when(roomRepository
                .findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetween(
                Pageable.unpaged(),
                hotel,
                true,
                RoomType.EXTRA_VIEW,
                minCost,
                maxCost))
                .thenReturn(expectedRooms);

        Page<Room> result = roomService.findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetween(
                Pageable.unpaged(),
                hotel,
                true,
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
                .findRoomsByHotelAndAvailability(Pageable.unpaged(),hotel, true)).thenReturn(Page.empty());

        assertThatThrownBy(() -> roomService
                .findAvailableRoomsByHotel(Pageable.unpaged(), hotel, true))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessage("Room List is empty.");
    }

    @Test
    void findAvailableRoomsByHotelAndRoomTypeShouldThrowRoomNotFoundExceptionWhenAllRoomsAreUnavailable() {
        when(roomRepository
                .findRoomsByHotelAndAvailabilityAndRoomType(Pageable.unpaged(), hotel, true, RoomType.REGULAR))
                .thenReturn(Page.empty());

        assertThatThrownBy(() -> roomService
                .findAvailableRoomsByHotelAndRoomType(Pageable.unpaged(), hotel, true, RoomType.REGULAR))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessage("Room List is empty.");
    }

    @Test
    void findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetweenShouldThrowRoomNotFoundExceptionWhenAllRoomsAreUnavailable() {
        when(roomRepository
                .findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetween(Pageable.unpaged(), hotel, true, RoomType.REGULAR, minCost, maxCost))
                .thenReturn(Page.empty());

        assertThatThrownBy(() -> roomService
                .findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetween(Pageable.unpaged(), hotel, true, RoomType.REGULAR, minCost, maxCost))
                .isInstanceOf(RoomNotFoundException.class)
                .hasMessage("Room List is empty.");
    }

}
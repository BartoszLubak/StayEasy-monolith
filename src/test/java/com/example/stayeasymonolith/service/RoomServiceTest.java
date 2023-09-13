package com.example.stayeasymonolith.service;

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

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void findRoomsByHotelShouldReturnCorrectPage() {
        when(roomRepository.findRoomsByHotel(hotel)).thenReturn(new PageImpl<>(roomsAtHotel));

        assertThat(roomService.findRoomsByHotel(hotel))
                .isNotNull()
                .hasSize(4)
                .extracting("roomNumber")
                .containsExactly(1, 2, 3, 4);
    }

    @Test
    void findAvailableRoomsByHotelShouldReturnAvailableRooms() {
        when(roomRepository.findRoomsByHotelAndAvailability(hotel, true)).thenReturn(new PageImpl<>(roomsAvailable));

        assertThat(roomService.findAvailableRoomsByHotel(hotel, true))
                .isNotNull()
                .hasSize(2)
                .extracting("roomNumber")
                .containsExactly(1, 4);
    }

    @Test
    void findAvailableRoomsByHotelAndRoomTypeShouldReturnAvailableRoomsWithCorrectType() {
        when(roomRepository.findRoomsByHotelAndAvailabilityAndRoomType(hotel, true, RoomType.EXTRA_VIEW))
                .thenReturn(new PageImpl<>(List.of(room4)));

        assertThat(roomService.findAvailableRoomsByHotelAndRoomType(hotel, true, RoomType.EXTRA_VIEW))
                .isNotNull()
                .hasSize(1)
                .extracting("roomNumber")
                .containsExactly(4);

        assertThat(roomService.findAvailableRoomsByHotelAndRoomType(hotel, true, RoomType.EXTRA_VIEW))
                .extracting("roomType")
                .containsExactly(RoomType.EXTRA_VIEW);
    }

    @Test
    void findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetweenShouldReturnCorrectRoomsInPriceRange() {
        PageImpl<Room> expectedRooms = new PageImpl<>(List.of(room2, room3, room4));

        when(roomRepository.findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetween(
                hotel,
                true,
                RoomType.EXTRA_VIEW,
                BigDecimal.valueOf(280),
                BigDecimal.valueOf(380)))
                .thenReturn(expectedRooms);

        Page<Room> result = roomService.findRoomsByHotelAndAvailabilityAndRoomTypeAndCostBetween(
                hotel,
                true,
                RoomType.EXTRA_VIEW,
                BigDecimal.valueOf(280),
                BigDecimal.valueOf(380));

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(3, result.getTotalElements()),
                () -> assertTrue(result.getContent().contains(room2)),
                () -> assertTrue(result.getContent().contains(room3)),
                () -> assertTrue(result.getContent().contains(room4))
        );
    }
}
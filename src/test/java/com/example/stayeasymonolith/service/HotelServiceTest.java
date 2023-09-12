package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.exceptions.HotelNotFoundException;
import com.example.stayeasymonolith.model.Address;
import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.model.HotelOwner;
import com.example.stayeasymonolith.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {
    @Mock
    private HotelRepository hotelRepository;
    @InjectMocks
    private HotelService hotelService;

    Hotel hotel1 = new Hotel(0, "Test0", new Address(), 5, List.of(), new HotelOwner());
    Hotel hotel2 = new Hotel(1, "Test1", new Address("PL", "Opole", "Test1", "1"), 5, List.of(), new HotelOwner());
    Hotel hotel3 = new Hotel(2, "Test1", new Address("PL", "Opole", "Test2", "2"), 5, List.of(), new HotelOwner());
    List<Hotel> hotelsInOpole = List.of(hotel2, hotel3);
    List<Hotel> hotels = List.of(hotel1, hotel2, hotel3);

    @Test
    void shouldFindHotelByIdReturnCorrectHotel() {
        long hotelId = 1;

        when(hotelRepository.findHotelById(hotelId)).thenReturn(Optional.of(hotel1));
        Hotel hotelWithGivenId = hotelService.findById(hotelId);

        assertAll("hotel",
                () -> assertEquals(hotel1.getId(), hotelWithGivenId.getId()),
                () -> assertEquals(hotel1.getName(), hotelWithGivenId.getName())
        );
    }

    @Test
    void findWithWrongIdShouldThrowHotelNotFoundException() {
        long hotelId = 0;

        when(hotelRepository.findHotelById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> hotelService.findById(hotelId))
                .isInstanceOf(HotelNotFoundException.class)
                .hasMessage("Hotel with given id: %s do not exist".formatted(hotelId));
    }

    @Test
    void findHotelsByNameOrAddress_CityShouldReturnCorrectHotelByName() {
        when(hotelRepository.findHotelsByName(Pageable.unpaged(), "Test0"))
                .thenReturn(new PageImpl<>(List.of(hotel1)));

        Page<Hotel> resultByName = hotelService.findHotelsByNameOrAddress_City(Pageable.unpaged(), "Test0", "");

        assertThat(resultByName)
                .isNotNull()
                .hasSize(1)
                .extracting("name")
                .containsExactly("Test0");
    }

    @Test
    void findHotelsByNameOrAddress_CityShouldReturnCorrectHotelByCity() {
        when(hotelRepository.findHotelsByAddress_City(Pageable.unpaged(), "Opole"))
                .thenReturn(new PageImpl<>(hotelsInOpole));

        Page<Hotel> resultByCity = hotelService.findHotelsByNameOrAddress_City(Pageable.unpaged(), "", "Opole");

        assertThat(resultByCity)
                .isNotNull()
                .hasSize(2) // Oczekiwana liczba wyników
                .extracting("name")
                .containsExactly("Test1", "Test1");
    }

    @Test
    void findHotelsByNameOrAddress_CityShouldReturnCorrectHotelByNameAndCity() {
        when(hotelRepository.findAllByNameAndAddress_City(Pageable.unpaged(), "Test1", "Opole"))
                .thenReturn(new PageImpl<>(hotelsInOpole));

        Page<Hotel> resultByNameAndCity = hotelService.findHotelsByNameOrAddress_City(Pageable.unpaged(), "Test1", "Opole");

        assertThat(resultByNameAndCity)
                .isNotNull()
                .hasSize(2)
                .extracting("name")
                .containsExactly("Test1", "Test1");
    }

    @Test
    void findHotelsByNameOrAddress_CityShouldThrowHotelNotFoundException() {
        when(hotelRepository.findAllByNameAndAddress_City(Pageable.unpaged(), "Test", "City"))
                .thenReturn(Page.empty());

        assertThatThrownBy(() -> hotelService.findHotelsByNameOrAddress_City(Pageable.unpaged(), "Test", "City"))
                .isInstanceOf(HotelNotFoundException.class)
                .hasMessage("Hotel list is empty.");
    }
}
package com.example.stayeasymonolith.controller;

import com.example.stayeasymonolith.exceptions.HotelNotFoundException;
import com.example.stayeasymonolith.model.Hotel;
import com.example.stayeasymonolith.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HotelController {
    public static final String HOTELS_VIEW = "/hotel/hotels";
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels-search")
    public String getHotelsByNameOrAddressCity(@RequestParam String name,
                                               @RequestParam String city,
                                               @SortDefault("name") Pageable pageable,
                                               Model model) {
        Page<Hotel> hotels = hotelService.findHotelsByNameOrAddressCity(pageable, name, city);
        model.addAttribute("hotels", hotels);
        return HOTELS_VIEW;
    }

    @GetMapping("/hotels-search-by-owner")
    public String getHotelsByHotelOwner(@RequestParam String hotelOwner,
                                        @SortDefault("name") Pageable pageable,
                                        Model model) {
        Page<Hotel> hotels = hotelService.findAllHotelsByHotelOwner(pageable, hotelOwner);
        model.addAttribute("hotels", hotels);
        return HOTELS_VIEW;
    }

    @GetMapping("/hotels/{id}")
    public String getHotelsById(@PathVariable Long id, Model model) {
        Hotel hotel = hotelService.findById(id);
        model.addAttribute("hotel", hotel);
        return "/hotel/hotel-details";
    }

    @GetMapping("/hotels")
    public String getAllHotels(@SortDefault("name") Pageable pageable, Model model) {
        Page<Hotel> hotels = hotelService.findAllHotels(pageable);
        model.addAttribute("hotels", hotels);
        return HOTELS_VIEW;
    }

    @GetMapping("/hotels/new")
    public String getNewHotel(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "/hotel/hotel-save";
    }

    @PostMapping("/hotels/new")
    public String addNewHotel (@ModelAttribute Hotel hotel){
        hotelService.saveHotel(hotel);
        return "redirect:/hotels/%d".formatted(hotel.getId());
    }

    @GetMapping ("/hotels/delete/{id}")
    public String deleteHotel (@PathVariable long id){
        hotelService.deleteHotel(hotelService.findById(id));
        return "redirect:/hotels";
    }

    @ExceptionHandler(HotelNotFoundException.class)
    public String HotelNotFoundExceptionHandler() {
        return "/hotel/hotel-searchException";
    }
}

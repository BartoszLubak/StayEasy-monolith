package com.example.stayeasymonolith.controller;


import com.example.stayeasymonolith.exceptions.RoomNotFoundException;
import com.example.stayeasymonolith.model.*;
import com.example.stayeasymonolith.service.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/reservation")
public class ReservationController {
    private final HotelService hotelService;
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final SessionService sessionService;
    private final ExtraService extraService;
    private final GuestService guestService;
    private final UserService userService;

    public ReservationController(HotelService hotelService, RoomService roomService, ReservationService reservationService, SessionService sessionService, ExtraService extraService, GuestService guestService, UserService userService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.reservationService = reservationService;
        this.sessionService = sessionService;
        this.extraService = extraService;
        this.guestService = guestService;
        this.userService = userService;
    }


    @GetMapping("/{hotelId}")
    public String getStep1FormAddDatesAndRoomType(@PathVariable long hotelId, Model model) {
        Hotel hotel = hotelService.findById(hotelId);
        model.addAttribute("hotel", hotel);
        model.addAttribute("roomType", roomService.getRoomTypeEnumNamesFromProperties(hotelService.roomTypesInHotel(hotel)));
        model.addAttribute("reservation", new Reservation());
        return "/reservation/reservation-step1";
    }

    @PostMapping("/dates")
    public String addDatesAndRoomType(@ModelAttribute Reservation reservation,
                                      @RequestParam long hotelId) {
        Hotel hotel = hotelService.findById(hotelId);
        List<Room> availableRooms = roomService
                .findAvailableHotelRoomsBetweenDates(Pageable.unpaged(), hotel, reservation);
        if (availableRooms.isEmpty()) {
            throw new RoomNotFoundException("No available rooms between dates.");
        } else {
            reservationService.addAvailableRoomToReservation(reservation, availableRooms);
            reservationService.updateReservationCostsWithoutExtras(reservation);
            sessionService.updateReservationInSession(reservation);
            return "redirect:/reservation/guests";
        }
    }

    @GetMapping("/guests")
    public String getStep2FormAddGuests(Model model) {
        model.addAttribute("guest", new Guest());
        model.addAttribute("guestList", sessionService.getReservationFromSession().getGuest());
        return "/reservation/reservation-step2";
    }

    @PostMapping("/add-guests")
    public String addGuestsToReservation(@ModelAttribute Guest guest) {
        Reservation reservation = sessionService.getReservationFromSession();
        Room room = reservation.getRooms().get(0); //TODO change reservation for 1 room
        if (!reservationService.checkRoomCapacity(reservation, room)) {
            reservationService.addGuestToReservation(guest, reservation);
            guestService.setReservation(reservation, reservation.getGuest());
            sessionService.updateReservationInSession(reservation);
            return "redirect:/reservation/guests";
        } else {
            return "redirect:/reservation/extras";  // TODO change button 'add' to 'next', remove redirect
        }
    }

    @GetMapping("/extras")
    public String getStep3FormExtras(Model model) {
        Reservation reservation = sessionService.getReservationFromSession();
        model.addAttribute("extras", new Extra());
        model.addAttribute("roomExtras", extraService.findExtrasByRoom(reservation.getRooms().get(0)));
        model.addAttribute("reservation", reservation);
        model.addAttribute("nights", ChronoUnit.DAYS.between(reservation.getCheckIn(), reservation.getCheckOut()));
        return "/reservation/reservation-step3";
    }

    @PostMapping("/add-extras")
    public String addExtrasToReservation(@RequestParam(value = "extras", required = false) List<Long> extrasIds) {
        Reservation reservation = sessionService.getReservationFromSession();
        List<Extra> selectedExtras = extraService.extrasByIds(extrasIds);
        BigDecimal allExtrasCost = extraService.getAllExtrasCost(selectedExtras);
        reservationService.addExtrasToReservation(reservation, selectedExtras);
        reservationService.addExtrasCostsToReservation(reservation, allExtrasCost);
        extraService.setReservation(reservation, reservation.getExtras());
        sessionService.updateReservationInSession(reservation);
        return "redirect:/reservation/summary";
    }


    @GetMapping("/summary")
    public String getSummary(Model model) {
        Reservation reservation = sessionService.getReservationFromSession();
        Room room = reservation.getRooms().get(0); //TODO .get(0)
        model.addAttribute("room", room);
        model.addAttribute("Extra", extraService.findExtrasByRoom(room));
        model.addAttribute("reservation", reservation);
        model.addAttribute("roomType", roomService.getRoomTypeEnumNamesFromProperties(Set.of(room.getRoomType())));
        model.addAttribute("nights", ChronoUnit.DAYS.between(reservation.getCheckIn(), reservation.getCheckOut()));
        model.addAttribute("roomCost", reservationService.getReservationRoomCostWithoutExtras(reservation));
        return "/reservation/reservation-step4-summary";
    }

    @PostMapping("/summary-confirm")
    public String confirmReservation() {
        User user = userService.getCurrentUser();
        Reservation reservation = sessionService.getReservationFromSession();
        user.getReservations().add(reservation);
        reservation.setUser(user);
        userService.save(user);
        reservationService.save(reservation);
        guestService.saveGuests(reservation.getGuest());
        roomService.saveRooms(reservation.getRooms());
        extraService.saveExtras(reservation.getExtras());
        sessionService.closeReservationSession();
        return "redirect:/"; //TODO redirect to Reservation success html
    }

}

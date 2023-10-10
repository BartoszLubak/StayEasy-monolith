package com.example.stayeasymonolith.service;

import com.example.stayeasymonolith.model.Guest;
import com.example.stayeasymonolith.model.Reservation;
import com.example.stayeasymonolith.model.Room;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SessionService {
    private final HttpSession httpSession;

    public SessionService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public Reservation getReservationFromSession (){
        return (Reservation) httpSession.getAttribute("reservation");
    }

    public void updateReservationInSession(Reservation reservation){
        httpSession.setAttribute("reservation", reservation);
        log.info("Session updated");
    }
    public void closeReservationSession(){
        httpSession.removeAttribute("reservation");
        log.info("Reservation session closed");
    }

}

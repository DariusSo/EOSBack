package com.EventOrganizationSystem.EOS.services;

import com.EventOrganizationSystem.EOS.models.Reservation;
import com.EventOrganizationSystem.EOS.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;


public class ReservationService {

    ReservationRepository rr = new ReservationRepository();
    EmailService ems = new EmailService();
    EventService es = new EventService();

    public BigDecimal addReservation(Reservation reservation, UUID uuid) throws SQLException {
        rr.addReservation(reservation, uuid);
        return BigDecimal.valueOf(es.getEventById(reservation.getEventId()).getPrice());
    }
    public Reservation getReservationByUUID(UUID uuid) throws SQLException {
        return rr.getReservationByUUID(uuid);
    }

}
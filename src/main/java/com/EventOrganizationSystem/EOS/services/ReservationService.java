package com.EventOrganizationSystem.EOS.services;

import com.EventOrganizationSystem.EOS.models.Reservation;
import com.EventOrganizationSystem.EOS.repositories.ReservationRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;


public class ReservationService {

    ReservationRepository rr = new ReservationRepository();

    EventService es = new EventService();

    public double addReservation(int userId, int eventId, UUID uuid) throws SQLException {
        rr.addReservation(userId, eventId, uuid);
        return es.getEventById(eventId).getPrice();
    }
    public Reservation getReservationByUUID(UUID uuid) throws SQLException {
        return rr.getReservationByUUID(uuid);
    }

}
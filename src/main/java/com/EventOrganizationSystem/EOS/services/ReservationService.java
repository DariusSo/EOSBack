package com.EventOrganizationSystem.EOS.services;

import com.EventOrganizationSystem.EOS.models.Event;
import com.EventOrganizationSystem.EOS.models.Reservation;
import com.EventOrganizationSystem.EOS.repositories.ReservationRepository;
import com.EventOrganizationSystem.EOS.utils.JwtDecoder;
import io.jsonwebtoken.Claims;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
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
    public List<Event> getReservationsListByUserId(String token) throws SQLException {
        Claims claims = JwtDecoder.decodeJwt(token);
        int userId = claims.get("UserId", Integer.class);
        return rr.getReservationsByUserId(userId);
    }

}
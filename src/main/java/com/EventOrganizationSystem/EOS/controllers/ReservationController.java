package com.EventOrganizationSystem.EOS.controllers;

import com.EventOrganizationSystem.EOS.models.Event;
import com.EventOrganizationSystem.EOS.models.Reservation;
import com.EventOrganizationSystem.EOS.services.ReservationService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    ReservationService rs = new ReservationService();

    @CrossOrigin
    @GetMapping("/byUserId")
    public ResponseEntity<List<Event>> getReservationsListByUserId(@RequestHeader("Authorization") String token) throws SQLException {
        List<Event> eventList = new ArrayList<>();
        try{
            eventList = rs.getReservationsListByUserId(token);
            return ResponseEntity.ok(eventList);
        }catch (SQLException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(eventList);
        }catch (JwtException e){
            e.printStackTrace();
            return  ResponseEntity.badRequest().body(eventList);
        }
    }

}

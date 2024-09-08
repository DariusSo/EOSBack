package com.EventOrganizationSystem.EOS.controllers;

import com.EventOrganizationSystem.EOS.models.Event;
import com.EventOrganizationSystem.EOS.models.Reservation;
import com.EventOrganizationSystem.EOS.services.ReservationService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try{
            List<Event> eventList = rs.getReservationsListByUserId(token);
            return ResponseEntity.ok(eventList);
        }catch (SQLException e){
            return ResponseEntity.internalServerError().body(null);
        }catch (JwtException e){
            return  new ResponseEntity<List<Event>>(HttpStatus.UNAUTHORIZED);
        }
    }
    @CrossOrigin
    @GetMapping("/attended")
    public ResponseEntity<List<Event>> getReservationsByUserIdAttended(@RequestHeader("Authorization") String token) throws SQLException {
        try{
            List<Event> eventList = rs.getReservationsByUserIdAttended(token);
            return ResponseEntity.ok(eventList);
        }catch (SQLException e){
            return ResponseEntity.internalServerError().body(null);
        }catch (JwtException e){
            return  new ResponseEntity<List<Event>>(HttpStatus.UNAUTHORIZED);
        }

    }
    @CrossOrigin
    @GetMapping("/notAttendedYet")
    public ResponseEntity<List<Event>> getReservationsByUserIdNotAttendedYet(@RequestHeader("Authorization") String token) throws SQLException {
        try{
            List<Event> eventList = rs.getReservationsByUserIdNotAttendedYet(token);
            return ResponseEntity.ok(eventList);
        }catch (SQLException e){
            return ResponseEntity.internalServerError().body(null);
        }catch (JwtException e){
            return  new ResponseEntity<List<Event>>(HttpStatus.UNAUTHORIZED);
        }
    }

}

package com.EventOrganizationSystem.EOS.controllers;

import com.EventOrganizationSystem.EOS.models.Event;
import com.EventOrganizationSystem.EOS.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {


    EventService es = new EventService();
    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody Event event) throws SQLException {
        try{
            es.addEvent(event);
            return ResponseEntity.ok("Event added");
        }catch (SQLException e){
            return ResponseEntity.badRequest().body("Check if your parameters are good");
        }
    }
    @CrossOrigin
    @GetMapping("/get")
    public ResponseEntity<List<Event>> getEvents() throws SQLException {
           return ResponseEntity.ok(es.getEvents());
    }
    @CrossOrigin
    @GetMapping("/getById")
    public ResponseEntity<Event> getEventById(int id){
        try{
            return ResponseEntity.ok(es.getEventById(id));
        }catch (SQLException e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}

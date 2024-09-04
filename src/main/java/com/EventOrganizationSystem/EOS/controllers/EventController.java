package com.EventOrganizationSystem.EOS.controllers;

import com.EventOrganizationSystem.EOS.models.Event;
import com.EventOrganizationSystem.EOS.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
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
        try{
            return ResponseEntity.ok(es.getEvents());
        }catch (SQLException e){
            return ResponseEntity.internalServerError().body(null);
        }

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
    @CrossOrigin
    @GetMapping("/getFiltered")
    public ResponseEntity<List<Event>> getEventsWithFilters(double minPrice, double maxPrice, String minDate, String maxDate, String category) throws SQLException {
        try{
            List<Event> eventList = es.getEventsWithFilters(minPrice, maxPrice, minDate, maxDate, category);
            return ResponseEntity.ok(eventList);
        }catch (SQLException e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @CrossOrigin
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getEventCategories() throws SQLException {
        try{
            List<String> categoryList = es.getEventCategories();
            return ResponseEntity.ok(categoryList);
        }catch (SQLException e){
            return ResponseEntity.internalServerError().body(null);
        }

    }
    @CrossOrigin
    @GetMapping("/search/title")
    public ResponseEntity<List<Event>> searchEventsByTitle(String text) throws SQLException {
        List<Event> eventList = new ArrayList<>();
        try{
            eventList = es.searchEventByTitle(text);
            if(eventList.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(eventList);
        }catch (SQLException e){
            return ResponseEntity.internalServerError().body(eventList);
        }
    }
}

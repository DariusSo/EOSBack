package com.EventOrganizationSystem.EOS.repositories;

import com.EventOrganizationSystem.EOS.models.Event;
import com.EventOrganizationSystem.EOS.utils.Connect;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class EventRepository {

    public void addEvent(Event event) throws SQLException {
        PreparedStatement pr = Connect.SQLConnection("INSERT INTO events (title,description,price,category,date_and_time,place, image_url) VALUES (?,?,?,?,?,?,?)");
        pr.setString(1, event.getTitle());
        pr.setString(2, event.getDescription());
        pr.setDouble(3, event.getPrice());
        pr.setString(4, event.getCategory());
        pr.setString(5, String.valueOf(LocalDateTime.now()));
        pr.setString(6, event.getPlace());
        pr.setString(7, event.getImageUrl());
        pr.execute();
    }
    public List<Event> getEvents() throws SQLException {
        List<Event> eventList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PreparedStatement pr = Connect.SQLConnection("SELECT * FROM events");
        ResultSet rs = pr.executeQuery();

        while(rs.next()){
            Event event = new Event(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
                    rs.getDouble("price"), rs.getString("category"),
                    LocalDateTime.parse(rs.getString("date_and_time"), formatter),
                    rs.getString("place"), rs.getString("image_url"));
            eventList.add(event);
        }
        return eventList;
    }
    public Event getEventById(int id) throws SQLException {
        Event event = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PreparedStatement pr = Connect.SQLConnection("SELECT * FROM events WHERE id = ?");
        pr.setInt(1, id);
        ResultSet rs = pr.executeQuery();

        if(rs.next()){
            event = new Event(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
                    rs.getDouble("price"), rs.getString("category"),
                    LocalDateTime.parse(rs.getString("date_and_time"), formatter),
                    rs.getString("place"), rs.getString("image_url"));

        }
        return event;
    }
    public List<Event> getEventsWithFilters(double minPrice, double maxPrice, String minDate, String maxDate, String category) throws SQLException {
        PreparedStatement pr = null;
        List<Event> eventList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(Objects.equals(category, "All")){
            pr = Connect.SQLConnection("SELECT * FROM events WHERE price > ? AND price < ? AND date_and_time > ? AND date_and_time < ?");
        }else {
            pr = Connect.SQLConnection("SELECT * FROM events WHERE price > ? AND price < ? AND date_and_time > ? AND date_and_time < ? AND category = ?");
            pr.setString(5, category);
        }
        pr.setDouble(1, minPrice);
        pr.setDouble(2, maxPrice);
        pr.setString(3, minDate);
        pr.setString(4, maxDate);

        ResultSet rs = pr.executeQuery();

        while(rs.next()){
            Event event = new Event(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
                    rs.getDouble("price"), rs.getString("category"),
                    LocalDateTime.parse(rs.getString("date_and_time"), formatter),
                    rs.getString("place"), rs.getString("image_url"));
            eventList.add(event);
        }
        return eventList;
    }
    public List<String> getEventCategories() throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("SELECT DISTINCT(category) FROM events");
        ResultSet rs = ps.executeQuery();
        List<String> categoryList = new ArrayList<>();
        while(rs.next()){
            categoryList.add(rs.getString("category"));
        }
        return categoryList;
    }


}

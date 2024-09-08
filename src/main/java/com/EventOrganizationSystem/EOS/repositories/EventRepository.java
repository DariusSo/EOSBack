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

    public void addEvent(Event event, int refund100, boolean refund24) throws SQLException {
        PreparedStatement pr = Connect.SQLConnection("INSERT INTO events (title,description,price,category,date_and_time,place, image_url, refund_100, refund_24) VALUES (?,?,?,?,?,?,?,?,?,?)");
        pr.setString(1, event.getTitle());
        pr.setString(2, event.getDescription());
        pr.setDouble(3, event.getPrice());
        pr.setString(4, event.getCategory());
        pr.setString(5, String.valueOf(LocalDateTime.now()));
        pr.setString(6, event.getPlace());
        pr.setString(7, event.getImageUrl());
        pr.setInt(8, refund100);
        pr.setBoolean(9, refund24);
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
    public List<Event> searchEventsByTitle(String text) throws SQLException {
        List<Event> eventList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PreparedStatement ps = Connect.SQLConnection("SELECT * FROM events WHERE title LIKE ?");
        ps.setString(1, "%" + text + "%");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Event event = new Event(rs.getInt("id"), rs.getString("title"), rs.getString("description"),
                    rs.getDouble("price"), rs.getString("category"),
                    LocalDateTime.parse(rs.getString("date_and_time"), formatter),
                    rs.getString("place"), rs.getString("image_url"));
            eventList.add(event);
        }
        return eventList;
    }
    public int getRefundDays100(int eventId) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("SELECT * FROM events WHERE id = ?");
        ps.setInt(1, eventId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            return rs.getInt("refund_100");
        }
        return -1;
    }
    public boolean getRefundDays24(int eventId) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("SELECT * FROM events WHERE id = ?");
        ps.setInt(1, eventId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            return rs.getBoolean("refund_24");
        }
        return false;
    }




}

package com.EventOrganizationSystem.EOS.services;

import com.EventOrganizationSystem.EOS.models.Event;
import com.EventOrganizationSystem.EOS.repositories.EventRepository;
import com.EventOrganizationSystem.EOS.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;


public class EventService {


    EventRepository er = new EventRepository();

    ReservationRepository rr = new ReservationRepository();

    public void addEvent(Event event) throws SQLException {
        er.addEvent(event);
    }
    public List<Event> getEvents() throws SQLException {
        return er.getEvents();
    }
    public Event getEventById(int id) throws SQLException {
        return er.getEventById(id);
    }
    public boolean setOrderPaymentStatusTrue(UUID uuid) throws SQLException {
        try{
            rr.setOrderPaymentStatusTrue(uuid);

            return true;
        }catch (SQLException e){
            return false;
        }

    }
    public List<Event> getEventsWithFilters(double minPrice, double maxPrice, String minDate, String maxDate, String category) throws SQLException {
        return er.getEventsWithFilters(minPrice, maxPrice, minDate, maxDate, category);
    }
    public List<String> getEventCategories() throws SQLException {
        return er.getEventCategories();
    }

}

package com.EventOrganizationSystem.EOS.repositories;

import com.EventOrganizationSystem.EOS.models.Reservation;
import com.EventOrganizationSystem.EOS.utils.Connect;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class ReservationRepository {

    public void addReservation(int userId, int eventId, UUID uuid) throws SQLException {
        PreparedStatement pr = Connect.SQLConnection("INSERT INTO reservations (user_id, event_id, uuid, payment_status) VALUES (?,?,?,?)");
        pr.setInt(1, userId);
        pr.setInt(2, eventId);
        pr.setString(3, String.valueOf(uuid));
        pr.setBoolean(4, false);
        pr.execute();
    }
    public void setOrderPaymentStatusTrue(UUID uuid) throws SQLException {
        PreparedStatement pr = Connect.SQLConnection("UPDATE reservations SET payment_status = ? WHERE uuid = ?");
        pr.setBoolean(1,true);
        pr.setString(2, String.valueOf(uuid));
        pr.execute();
    }
    public Reservation getReservationByUUID(UUID uuid) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("SELECT * FROM reservations WHERE uuid = ?");
        ps.setString(1, String.valueOf(uuid));
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return new Reservation(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("event_id"));
        }
        return null;
    }
    public double checkDiscount(String code) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("SELECT * FROM discounts WHERE code = ? LIMIT 1");
        ps.setString(1, code);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            PreparedStatement psDelete = Connect.SQLConnection("DELETE FROM discounts WHERE code = ?");
            psDelete.setString(1, code);
            psDelete.execute();
            double discount = rs.getDouble("discount");
            return discount;
        }else{
            return 1.00;
        }
    }

}

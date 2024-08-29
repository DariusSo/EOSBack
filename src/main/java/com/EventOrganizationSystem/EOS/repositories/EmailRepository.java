package com.EventOrganizationSystem.EOS.repositories;

import com.EventOrganizationSystem.EOS.models.Email;
import com.EventOrganizationSystem.EOS.utils.Connect;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EmailRepository {

    public void registerSentEmail(Email email) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("INSERT INTO emails (user_email, type, date, is_sent, error) VALUES (?,?,?,?,?)");
        ps.setString(1, email.getUserEmail());
        ps.setString(2, email.getType());
        ps.setString(3, String.valueOf(LocalDateTime.now()));
        ps.setBoolean(4, email.isSent());
        ps.setString(5, email.getError());
        ps.execute();
    }
    public void addNewsletterSubscriber(String email) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("INSERT INTO newsletter (email) VALUES (?)");
        ps.setString(1, email);
        ps.execute();
    }

}

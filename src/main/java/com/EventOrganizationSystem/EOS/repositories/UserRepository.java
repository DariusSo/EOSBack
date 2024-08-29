package com.EventOrganizationSystem.EOS.repositories;

import com.EventOrganizationSystem.EOS.models.User;
import com.EventOrganizationSystem.EOS.utils.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public User getUserById(int id) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("SELECT * FROM user WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return new User(rs.getInt("id"), rs.getString("email"));
        }
        return null;
    }
    public void createUser(User user) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("INSERT INTO user (email, password) VALUES (?,?)");
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.execute();
    }
    public int login(User user) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("SELECT * FROM user WHERE email = ? AND password = ?");
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int userId = rs.getInt("id");
            return userId;
        }
        return -1;
    }

}

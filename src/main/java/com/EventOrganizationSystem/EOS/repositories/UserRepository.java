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

}

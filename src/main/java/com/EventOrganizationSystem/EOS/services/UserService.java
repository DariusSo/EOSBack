package com.EventOrganizationSystem.EOS.services;

import com.EventOrganizationSystem.EOS.models.User;
import com.EventOrganizationSystem.EOS.repositories.UserRepository;

import java.sql.SQLException;

public class UserService {
    UserRepository ur = new UserRepository();

    public User getUserById(int id) throws SQLException {
        return ur.getUserById(id);
    }
}

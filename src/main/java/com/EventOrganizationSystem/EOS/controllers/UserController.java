package com.EventOrganizationSystem.EOS.controllers;

import com.EventOrganizationSystem.EOS.models.User;
import com.EventOrganizationSystem.EOS.services.UserService;
import com.EventOrganizationSystem.EOS.utils.JwtGenerator;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService us = new UserService();

    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user){
        try{
            us.createUser(user);
            return ResponseEntity.ok("Success");
        } catch (SQLException e) {
            e.printStackTrace();
           return ResponseEntity.internalServerError().body("Server problem");
        } catch (JwtException e){
            return ResponseEntity.internalServerError().body("Server problem");
        }
    }
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) throws SQLException {
        try{
            int userId = us.login(user);
            if(userId != -1){return ResponseEntity.ok(JwtGenerator.generateJwt(user.getId()));
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch (SQLException e){
            return ResponseEntity.internalServerError().body("Server problems");
        }
    }


}

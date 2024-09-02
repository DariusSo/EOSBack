package com.EventOrganizationSystem.EOS.controllers;

import com.EventOrganizationSystem.EOS.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
@RestController
public class EmailController {

    EmailService ems = new EmailService();

    @CrossOrigin
    @PostMapping("/subscribe")
    public void addNewsletterSubscriber(String email) throws SQLException {
        ems.addNewsletterSubscriber(email);
    }

    @CrossOrigin
    @PostMapping("/send/newsletter")
    public void sendNewsLetterEmailToAll(String text) throws SQLException {
        ems.sendNewsletterEmailToAll(text);
    }

}

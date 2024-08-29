package com.EventOrganizationSystem.EOS.controllers;

import com.EventOrganizationSystem.EOS.models.Reservation;
import com.EventOrganizationSystem.EOS.services.EmailService;
import com.EventOrganizationSystem.EOS.services.EventService;
import com.EventOrganizationSystem.EOS.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class StripeController {

    StripeService sr = new StripeService();
    EmailService ems = new EmailService();
    EventService es = new EventService();

    @CrossOrigin
    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestHeader("Authorization") String token, int eventId,
                                                                     @RequestParam(value = "code", required = false) String code) {
        try {
            Session session = sr.createCheckoutSession(token, eventId, code);
            Map<String, String> responseData = new HashMap<>();
            responseData.put("id", session.getId());
            return ResponseEntity.ok(responseData);
        } catch (StripeException | SQLException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/redirect")
    public RedirectView redirect(UUID uuid) throws SQLException {
        if(es.setOrderPaymentStatusTrue(uuid)){
            ems.sendConfirmationEmail(uuid);
        }
        return (es.setOrderPaymentStatusTrue(uuid)) ?
                new RedirectView("http://localhost:5173/"): // status update successful
                new RedirectView("http://localhost:7777/errorPaying.html"); // status update failed
    }


}

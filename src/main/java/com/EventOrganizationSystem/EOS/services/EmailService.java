package com.EventOrganizationSystem.EOS.services;

import com.EventOrganizationSystem.EOS.models.Event;
import com.EventOrganizationSystem.EOS.models.Reservation;
import com.EventOrganizationSystem.EOS.models.User;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public class EmailService {

    UserService us = new UserService();
    EventService es = new EventService();
    ReservationService rs = new ReservationService();

    public void sendConfirmationEmail(UUID uuid) throws SQLException {
        Reservation reservation = rs.getReservationByUUID(uuid);
        User user = us.getUserById(reservation.getUserId());
        Event event = es.getEventById(reservation.getEventId());
        Email from = new Email("DariusSSpam@gmail.com");
        String subject = "Thank you for registering for event. Event name:" + event.getTitle() + "|| Date: " + event.getDateAndTime();
        Email to = new Email(user.getEmail());
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {

        }
    }

}

package com.EventOrganizationSystem.EOS.services;

import com.EventOrganizationSystem.EOS.models.Event;
import com.EventOrganizationSystem.EOS.models.Reservation;
import com.EventOrganizationSystem.EOS.models.User;
import com.EventOrganizationSystem.EOS.repositories.EmailRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class EmailService {

    UserService us = new UserService();
    EventService es = new EventService();
    ReservationService rs = new ReservationService();
    EmailRepository er = new EmailRepository();

    public void sendConfirmationEmail(UUID uuid) throws SQLException {
        Reservation reservation = rs.getReservationByUUID(uuid);
        User user = us.getUserById(reservation.getUserId());
        Event event = es.getEventById(reservation.getEventId());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Email from = new Email("DariusSSpam@gmail.com");
        String subject = "Thank you for registering for event!";
        Email to = new Email(user.getEmail());
        Content content = new Content("text/plain", "Event name:" + event.getTitle() + "|| Date: " + event.getDateAndTime());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            if(response.getStatusCode() < 299 && response.getStatusCode() > 199){
                com.EventOrganizationSystem.EOS.models.Email email = new com.EventOrganizationSystem.EOS.models.Email(user.getEmail(), "Confirmation", true, "");
                er.registerSentEmail(email);
            }else {
                com.EventOrganizationSystem.EOS.models.Email email = new com.EventOrganizationSystem.EOS.models.Email(user.getEmail(), "Confirmation", false,
                        String.valueOf(response.getStatusCode() + " || " + response.getBody()));
                er.registerSentEmail(email);
            }
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {

        }
    }
    public void addNewsletterSubscriber(String userEmail) throws SQLException {
        Email from = new Email("DariusSSpam@gmail.com");
        String subject = "Thank you for registering for event!";
        Email to = new Email(userEmail);
        Content content = new Content("text/plain", "Thank you for subscribing our news letter.");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            er.addNewsletterSubscriber(userEmail);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            if(response.getStatusCode() < 299 && response.getStatusCode() > 199){
                com.EventOrganizationSystem.EOS.models.Email email = new com.EventOrganizationSystem.EOS.models.Email(userEmail, "Newsletter", true, "");
                er.registerSentEmail(email);
            }else {
                com.EventOrganizationSystem.EOS.models.Email email = new com.EventOrganizationSystem.EOS.models.Email(userEmail, "Newsletter", false,
                        String.valueOf(response.getStatusCode() + " || " + response.getBody()));
                er.registerSentEmail(email);
            }

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
    public void sendNewsLetterEmail(String text, String emailUser) throws SQLException {

        Email from = new Email("DariusSSpam@gmail.com");
        String subject = "Check this out!";
        Email to = new Email(emailUser);
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(from, subject, to, content);


        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            if(response.getStatusCode() < 299 && response.getStatusCode() > 199){
                com.EventOrganizationSystem.EOS.models.Email email = new com.EventOrganizationSystem.EOS.models.Email(emailUser, "Newsletter", true, "");
                er.registerSentEmail(email);
            }else {
                com.EventOrganizationSystem.EOS.models.Email email = new com.EventOrganizationSystem.EOS.models.Email(emailUser, "Newsletter", false,
                        String.valueOf(response.getStatusCode() + " || " + response.getBody()));
                er.registerSentEmail(email);
            }
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public List<String> getNewsletterEmails() throws SQLException {
        return er.getNewsletterEmails();
    }
    public void sendNewsletterEmailToAll(String text) throws SQLException {
        List<String> emailList = getNewsletterEmails();
        for(String email : emailList){
            sendNewsLetterEmail(text, email);
        }
    }

}

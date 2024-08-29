package com.EventOrganizationSystem.EOS.services;

import com.EventOrganizationSystem.EOS.models.Reservation;
import com.EventOrganizationSystem.EOS.repositories.ReservationRepository;
import com.EventOrganizationSystem.EOS.utils.JwtDecoder;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;


public class StripeService {


    ReservationService rs = new ReservationService();
    ReservationRepository rr = new ReservationRepository();

    @Value("${stripe.api.key}")
    private String stripeApiKey;


    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public PaymentIntent createPaymentIntent(Long amount, String currency) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amount)
                        .setCurrency(currency)
                        .build();

        return PaymentIntent.create(params);
    }

    public Session createCheckoutSession(String token, int eventId, String code) throws StripeException, SQLException {
        Claims claims = JwtDecoder.decodeJwt(token);

        Stripe.apiKey = "sk_test_51PlEGq2KAAK191iLBzP39TlQrdJc52LgmEg8axaHojCGK5KZbMPylEJWoYiJ0MP3jrwexCzBDwgHVOCwAfWYVEQD00Z6gOC4wD";
        double discount = 0;
        UUID uuid = UUID.randomUUID();
        double amount = rs.addReservation((Integer) claims.get("UserId"), eventId, uuid);
        if(amount == 0)
            return new Session();
        if(code == null){
            discount = 1;
        }else{
            discount = rr.checkDiscount(code);
        }

        if(discount == 1.00){

        }else{

            amount = amount - (amount * discount);
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/redirect?uuid=" + uuid)
                .setCancelUrl("http://localhost:7777/index.html")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("eur")
                                .setUnitAmount((long) (amount * 100))
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Event reservation")
                                        .build())
                                .build())
                        .build())
                .build();

        return Session.create(params);
    }




}

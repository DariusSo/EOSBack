package com.EventOrganizationSystem.EOS.services;

import com.EventOrganizationSystem.EOS.models.Review;
import com.EventOrganizationSystem.EOS.repositories.ReviewRepository;
import com.EventOrganizationSystem.EOS.utils.JwtDecoder;
import io.jsonwebtoken.Claims;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewService {

    ReviewRepository rvr = new ReviewRepository();

    public void addReview(String token, Review review) throws SQLException {
        Claims claims = JwtDecoder.decodeJwt(token);
        int userId = claims.get("UserId", Integer.class);
        review.setUser_id(userId);
        rvr.addReview(review);
    }
    public List<Review> getReviewsByEventId(int eventId) throws SQLException {
        return rvr.getReviewsByEventId(eventId);
    }
}

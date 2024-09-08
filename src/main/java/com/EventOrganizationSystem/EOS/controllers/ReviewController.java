package com.EventOrganizationSystem.EOS.controllers;

import com.EventOrganizationSystem.EOS.models.Review;
import com.EventOrganizationSystem.EOS.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@RestController
public class ReviewController {

    ReviewService rvs = new ReviewService();
    @CrossOrigin
    @PostMapping("/add/review")
    public ResponseEntity<String> addReview(@RequestHeader("Authorization") String token, @RequestBody Review review){
        try{
            rvs.addReview(token, review);
            return ResponseEntity.ok("Success!");
        }catch (SQLException e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Server problems");
        }
    }
    @CrossOrigin
    @GetMapping("get/reviewsById")
    public ResponseEntity<List<Review>> getReviewsByEventId(int eventId) throws SQLException {
        try{
            return ResponseEntity.ok(rvs.getReviewsByEventId(eventId));
        }catch (SQLException e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @CrossOrigin
    @GetMapping("get/ratingData")
    public HashMap<String, Integer> reviewCount(int eventId) throws SQLException {
        return rvs.reviewCount(eventId);
    }
}

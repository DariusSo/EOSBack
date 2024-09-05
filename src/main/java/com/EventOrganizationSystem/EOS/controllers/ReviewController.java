package com.EventOrganizationSystem.EOS.controllers;

import com.EventOrganizationSystem.EOS.models.Review;
import com.EventOrganizationSystem.EOS.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ReviewController {

    ReviewService rvs = new ReviewService();
    @PostMapping("/add/review")
    public ResponseEntity<String> addReview(@RequestHeader("Authorization") String token, @RequestBody Review review){
        try{
            rvs.addReview(token, review);
            return ResponseEntity.ok("Success!");
        }catch (SQLException e){
            return ResponseEntity.internalServerError().body("Server problems");
        }
    }
    @GetMapping("get/reviewsById")
    public ResponseEntity<List<Review>> getReviewsByEventId(int eventId) throws SQLException {
        try{
            return ResponseEntity.ok(rvs.getReviewsByEventId(eventId));
        }catch (SQLException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}

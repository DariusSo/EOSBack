package com.EventOrganizationSystem.EOS.repositories;

import com.EventOrganizationSystem.EOS.models.Review;
import com.EventOrganizationSystem.EOS.utils.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {

    public void addReview(Review review) throws SQLException {
        PreparedStatement ps = Connect.SQLConnection("INSERT INTO reviews (user_id, event_id, review) VALUES (?,?,?)");
        ps.setInt(1, review.getUser_id());
        ps.setInt(2, review.getEvent_id());
        ps.setString(3, review.getReview());
        ps.execute();
    }
    public List<Review> getReviewsByEventId(int eventId) throws SQLException {
        List<Review> reviewList = new ArrayList<>();
        PreparedStatement ps = Connect.SQLConnection("SELECT * FROM reviews WHERE event_id = ?");
        ps.setInt(1, eventId);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Review review = new Review(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("event_id"), rs.getString("review"));
            reviewList.add(review);
        }
        return reviewList;
    }

}

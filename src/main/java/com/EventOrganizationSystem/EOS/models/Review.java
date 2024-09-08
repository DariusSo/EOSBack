package com.EventOrganizationSystem.EOS.models;

public class Review {
    private int id;
    private int user_id;
    private int event_id;
    private String review;
    private int rating;
    private String username;

    public Review(int id, int user_id, int event_id, String review, int rating, String username) {
        this.id = id;
        this.user_id = user_id;
        this.event_id = event_id;
        this.review = review;
        this.rating = rating;
        this.username = username;
    }

    public Review() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

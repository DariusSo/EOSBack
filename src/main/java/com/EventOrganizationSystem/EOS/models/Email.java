package com.EventOrganizationSystem.EOS.models;

import java.time.LocalDateTime;

public class Email {
    private int id;
    private String userEmail;
    private String type;
    private LocalDateTime date;
    private boolean isSent;

    public Email(int id, String userEmail, String type, LocalDateTime date, boolean isSent) {
        this.id = id;
        this.userEmail = userEmail;
        this.type = type;
        this.date = date;
        this.isSent = isSent;
    }

    public Email() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}

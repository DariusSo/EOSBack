package com.EventOrganizationSystem.EOS.models;

public class Admin {
    private String id;
    private String username;
    private String password;

    public Admin(String username, String id, String password) {
        this.username = username;
        this.id = id;
        this.password = password;
    }

    public Admin() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.trabajito;

import com.google.gson.annotations.SerializedName;


public class LoginResponse {
    @SerializedName("user")

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

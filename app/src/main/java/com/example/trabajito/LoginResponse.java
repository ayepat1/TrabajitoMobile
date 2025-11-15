package com.example.trabajito;

import com.example.trabajito.classes.User;
import com.google.gson.annotations.SerializedName;


public class LoginResponse {
    @SerializedName("user")

    private User user;
    private String token;

    public User getUser() {
        return user;
    }

    public String getToken() { return token; }


    public void setUser(User user) {
        this.user = user;
    }
}

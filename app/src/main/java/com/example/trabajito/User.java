package com.example.trabajito;

import com.google.gson.annotations.SerializedName;


public class User {
@SerializedName("nombre")
    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}

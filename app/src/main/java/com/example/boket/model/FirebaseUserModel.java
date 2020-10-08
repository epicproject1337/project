package com.example.boket.model;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseUserModel {
    private String uid;
    private String email;
    private String name;
    private String location;

    public FirebaseUserModel(String uid, String email, String name, String location) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.location = location;
    }

    public FirebaseUserModel() {
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}

package com.example.boket.model;

import com.example.boket.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

//TODO: move all user "logic" and firebase auth calls to a User Model.
public class User {

    private int uid;
    private String email;
    private String name;
    private String location;

    private FirebaseAuth mAuth;
    private static final String TAG = User.class.getName();

    public User() {
        mAuth = FirebaseAuth.getInstance();
    }

    public int getUid() {
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

    public void signup(String name, String email, String password, String location) {
        //TODO
    }

    public void login(String email, String password) {
        //TODO
    }

    public void signout() {
        //TODO
    }
}
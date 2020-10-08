package com.example.boket.model;

import com.example.boket.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

//TODO: move all user "logic" and firebase auth calls to a User Model.
public class User {

    private String uid;
    private String email;
    private String name;
    private String location;

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = User.class.getName();

    public User(String uid, String email, String name, String location) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.location = location;
    }

    private User() {

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
        //TODO if location is null get from database.
        return location;
    }

    private void setLocation(String loc){
        this.location = loc;
    }

    public void signup(String name, String email, String password, String location) {
        //TODO
    }

    public void login(String email, String password) {
        //TODO
    }

    public static void signout() {
        mAuth.signOut();
    }

    public static User getCurrentUser(){
        FirebaseUser u = mAuth.getCurrentUser();
        if (u != null){
            return new User(u.getUid(), u.getEmail(), u.getDisplayName(), null);
        }else {
            return null;
        }
    }
}
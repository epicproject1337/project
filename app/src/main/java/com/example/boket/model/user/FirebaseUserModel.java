package com.example.boket.model.user;

import com.google.firebase.auth.FirebaseAuth;
/**
 * @author Pajam Khoshnam
 * An object representing a User for the Firebase Firestore Database
 * Used to save/update/add a User to the Firebase Firestore Database
 * @since 2020-10-08
 */
class FirebaseUserModel implements User {
    private String uid;
    private String email;
    private String name;
    private String location;

    /**
     * @param uid Users ID
     * @param email Users email
     * @param name Users name
     * @param location Users location
     */
    public FirebaseUserModel(String uid, String email, String name, String location) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.location = location;
    }

    public FirebaseUserModel() {
    }

    /**
     * @return The user ID.
     */
    public String getUid() {
        return uid;
    }

    /**
     * @return The users email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Users name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The users Location
     */
    public String getLocation() {
        return location;
    }

}

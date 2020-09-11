package com.example.boket.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Ad {
    private int id;
    private FirebaseUser user;
    private String isbn;
    private double price;
    private String adType; // "sell" or "buy"
    private String condition = null; // null if adType is buy (default value)
    private boolean archived;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String collection = "ads";

    public Ad() {}

    public Ad(int id, FirebaseUser user, String isbn, double price, String adType, String condition, boolean archived) {
        this.id = id;
        this.user = user;
        this.isbn = isbn;
        this.price = price;
        this.adType = adType;
        this.condition = condition;
        this.archived = archived;
    }

    public Ad(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getPrice() {
        return price;
    }

    public String getAdType() {
        return adType;
    }

    public String getCondition() {
        return condition;
    }

    public boolean isArchived() {
        return archived;
    }

    public void create(){
        //TODO : Add validation to make sure 1. all fields are set and valid
        db.collection(collection).add(this);
    }

    private void loadData(Ad ad, int id){
        this.id = id;
        this.user = ad.getUser();
        this.isbn = ad.getIsbn();
        this.price = ad.getPrice();
        this.adType = ad.getAdType();
        this.condition = ad.getCondition();
        this.archived = ad.isArchived();
    }
}

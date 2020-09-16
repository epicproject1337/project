package com.example.boket.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Ad {
    private int id;
    private FirebaseUser user;
    private String isbn;
    private double price;
    private String adType; // "sell" or "buy"
    private String condition = null; // null if adType is buy (default value)
    private boolean archived;

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String collection = "ads";
    private static final String TAG = Ad.class.getName();

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

    public static ArrayList<Ad> getAdsByISBN(String isbn){

        final ArrayList<Ad> adList = new ArrayList<Ad>();

        Query docRef = db.collection(collection).whereEqualTo("isbn", isbn);
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Ad ad = document.toObject(Ad.class);
                        adList.add(ad);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        return adList;
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

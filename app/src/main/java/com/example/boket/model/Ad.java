package com.example.boket.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Ad{
    private int id;
    private String userId;
    private String isbn;
    private double price;
    private String condition = null;
    private boolean archived;
    @ServerTimestamp
    private Timestamp timeUpdated = null;

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String collection = "ads";
    private static final String TAG = Ad.class.getName();

    public Ad() {
    }

    public Ad(int id, String userId, String isbn, double price, String condition, boolean archived) {
        this.id = id;
        this.userId = userId;
        this.isbn = isbn;
        this.price = price;
        this.condition = condition;
        this.archived = archived;
    }

    public Ad(int id) {
        this.id = id;
    }

    public static void getAdsByISBN(String isbn, GetAdsCallback callback) {

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
                    callback.onGetAdsComplete(adList);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    public boolean isArchived() {
        return archived;
    }

    public void save() {
        //TODO : Add validation to make sure 1. all fields are set and valid
        db.collection(collection).add(this);
    }

    public Timestamp getTimeUpdated() {
        return timeUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return id == ad.id &&
                Double.compare(ad.price, price) == 0 &&
                archived == ad.archived &&
                userId.equals(ad.userId) &&
                isbn.equals(ad.isbn) &&
                condition.equals(ad.condition);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, userId, isbn, price, condition, archived);
    }

    public interface GetAdsCallback {
        void onGetAdsComplete(ArrayList<Ad> adList);
    }
}

package com.example.boket.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;

public class Subscription {

    private String userId;
    private String isbn;
    @ServerTimestamp
    private Timestamp timeCreated = null;

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String collection = "subscriptions";

    public Subscription() {
    }

    private Subscription(String isbn, String userId){
        this.isbn = isbn;
        this.userId = userId;
    }

    public static void subscribeUser(String isbn, String userId){
        Subscription s = new Subscription(isbn, userId);
        String docId = userId + isbn;
        db.collection(collection).document(docId).set(s);
    }

    public static void isSubscribed(String isbn, String userId, OnLoadCallback callback){
        String docId = userId + isbn;
        DocumentReference docRef = db.collection(collection).document(docId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                callback.isSubscribedCallback(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.isSubscribedCallback(false);
            }
        });
    }

    public static void getSubscribedBooks(String userId, OnLoadSubscribedBooksCallback callback){
        //TODO Get all books that a user is subscribed to
    }

    public String getUserId() {
        return userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public interface OnLoadCallback{
        void isSubscribedCallback(boolean isSubscribed);
    }
    public interface OnLoadSubscribedBooksCallback{
        void onCompleteCallback(ArrayList<Book> books);
    }
}

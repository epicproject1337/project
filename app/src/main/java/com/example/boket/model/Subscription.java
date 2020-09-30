package com.example.boket.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Subscription {

    private String userId;
    private String isbn;
    @ServerTimestamp
    private Timestamp timeCreated = null;

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String collection = "subscriptions";

    public Subscription() {
    }

    private Subscription(String isbn, String userId) {
        this.isbn = isbn;
        this.userId = userId;
    }

    public static void subscribeUser(String isbn, String userId) {
        Subscription s = new Subscription(isbn, userId);
        String docId = userId + isbn;
        db.collection(collection).document(docId).set(s);
    }

    public static void isSubscribed(String isbn, String userId, OnLoadCallback callback) {
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

    public static void getSubscribedBooks(String userId, OnLoadSubscribedBooksCallback callback) {
        final ArrayList<Book> bookList = new ArrayList<Book>();
        Query docRef = db.collection(collection).whereEqualTo("userId", userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot result = task.getResult();
                    int i = 0;
                    for (QueryDocumentSnapshot document : result) {
                        int finalI = i;
                        Subscription subscription = document.toObject(Subscription.class);
                        Book tmpBook = new Book(subscription.getIsbn(), new Book.OnLoadCallback() {
                            @Override
                            public void onLoadComplete(Book book) {
                                bookList.add(book);
                                if (finalI == result.size() - 1) {
                                    callback.onCompleteCallback(bookList);
                                }
                            }
                        });
                        i++;
                    }

                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
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

    public interface OnLoadCallback {
        void isSubscribedCallback(boolean isSubscribed);
    }

    public interface OnLoadSubscribedBooksCallback {
        void onCompleteCallback(ArrayList<Book> books);
    }
}

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
/**
 * @author Pajam Khoshnam, Albin Landgren
 *
 * An object representing users subscribtions to a book.
 * Handles all the database adds/updates/gets for subscribtions.
 *
 * @since 2020-09-29
 */
public class Subscription {

    private String userId;
    private String isbn;
    @ServerTimestamp
    private Timestamp timeCreated = null;

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String collection = "subscriptions";

    /**
     * Create an "empty" subscription object with all the instance variables set to null.
     */
    public Subscription() {
    }

    private Subscription(String isbn, String userId) {
        this.isbn = isbn;
        this.userId = userId;
    }

    /**
     * Subscribe a user to a book
     * @param isbn isbn to subscribe to
     * @param userId User ID of the user.
     */
    public static void subscribeUser(String isbn, String userId) {
        //TODO: validate the parameters
        Subscription s = new Subscription(isbn, userId);
        String docId = userId + isbn;
        db.collection(collection).document(docId).set(s);
    }

    /**
     * Unsubscribe a user for a book.
     * @param isbn the isbn number of the book
     * @param userId the user id of the user
     */
    public static void unsubscribeUser(String isbn, String userId) {
        String docId = userId + isbn;
        db.collection(collection).document(docId).delete();
    }


    /**
     * Check if a user is subscribed to a book
     * @param isbn isbn number of the book
     * @param userId the user id of the user
     * @param callback callback method that will receive the boolean return value.
     */
    public static void isSubscribed(String isbn, String userId, OnLoadCallback callback) {
        String docId = userId + isbn;
        DocumentReference docRef = db.collection(collection).document(docId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                callback.isSubscribedCallback(documentSnapshot.exists());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.isSubscribedCallback(false);
            }
        });
    }

    /**
     * get all books that a user is subscribed to.
     * @param userId the user ID
     * @param callback callback method that will receive an ArrayList of Books that the user
     *                 is subscribed to.
     */
    public static void getSubscribedBooks(String userId, OnLoadSubscribedBooksCallback callback) {
        final ArrayList<Book> bookList = new ArrayList<Book>();
        Query docRef = db.collection(collection).whereEqualTo("userId", userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot result = task.getResult();
                    int i = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        int finalI = i;
                        Subscription subscription = document.toObject(Subscription.class);
                        Book tmpBook = new Book(subscription.getIsbn(), new Book.OnLoadCallback() {
                            @Override
                            public void onLoadComplete(Book book) {
                                bookList.add(book);
                                if (finalI == task.getResult().size() - 1) {
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

    /**
     * @return the user id of the subscription.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @return the isbn of the subscription
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @return the time the subscription was created.
     */
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

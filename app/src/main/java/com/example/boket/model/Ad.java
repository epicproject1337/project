package com.example.boket.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.boket.controller.Notifier;
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

/**
 * @author Pajam Khoshnam, Albin Landgren
 * <p>
 * An object representing an Ad.
 * Handles all the database adds/updates/gets for Ads.
 * @since 2020-09-10
 */
public class Ad {
    private int id; //TODO: Should we remove it?
    private String userId;
    private String email;
    private String isbn;
    private String imageUrl;
    private String city;
    private double price;
    private String condition = null;
    private boolean archived;
    @ServerTimestamp
    private Timestamp timeUpdated = null;
    private Book book = null;

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String collection = "ads";
    private static final String TAG = Ad.class.getName();


    /**
     * Create an "empty" Ad object with all the instance variables set to null.
     */
    public Ad() {
    }

    /**
     * Create an Ad object with all the necessary fields.
     *
     * @param userId    the user ID of the ad creator
     * @param email     the email of the ad creator
     * @param isbn      ISBN-number of the book which the ad is for.
     * @param price     Price for the book
     * @param condition Condition of the book
     * @param archived  If the ad should be marked as archived/sold. //TODO should always be false
     *                  on creating?
     */
    public Ad(String userId, String email, String isbn, double price, String condition, String city, boolean archived) {
        this.userId = userId;
        this.email = email;
        this.isbn = isbn;
        this.price = price;
        this.condition = condition;
        this.archived = archived;
        this.city = city;
    }

    /**
     * Get all ads created by a specific user
     *
     * @param userId   the user id of the creator
     * @param callback callback method which will receive an ArrayList of Ad with all the ads
     *                 belonging to the specific user.
     */
    public static void getAdsByUser(String userId, GetAdsCallback callback) {
        final ArrayList<Ad> adList = new ArrayList<Ad>();

        Query docRef = db.collection(collection).whereEqualTo("userId", userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Ad ad = document.toObject(Ad.class);
                        if (!ad.isArchived()) {
                            adList.add(ad);
                        }
                    }
                    callback.onGetAdsComplete(adList);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    /**
     * Get all ads belonging to a specific book.
     *
     * @param isbn     ISBN number of the book
     * @param callback method which will receive an ArrayList of Ad with all the ads
     *                 *                 belonging to the specific book.
     */
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

    /**
     * @return the ad ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return the user id of the ad creator
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @return the email of the ad creator
     */
    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    /**
     * @return isbn number of the book that the ad is for.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @return image link of the book that the ad is for.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @return price for the book
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return condition of the book
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @return if the book is archived/sold or not
     */
    public boolean isArchived() {
        return archived;
    }

    /**
     *
     */
    public void setArchived(boolean value) {
        this.archived = value;
    }

    /**
     * Saves the current Ad object to the database.
     */
    public void save() {
        Ad ad = this;
        Book book = new Book(this.getIsbn(), new Book.OnLoadCallback() {
            @Override
            public void onLoadComplete(Book book) {
                //TODO : Add validation to make sure 1. all fields are set and valid
                ad.imageUrl = book.getImage();
                db.collection(collection).add(ad);
                //Notifier.notifyUsersAboutNewAd(this.getIsbn());
            }
        });
    }


    /**
     * @return timestamp of when the ad was updated last.
     */
    public Timestamp getTimeUpdated() {
        return timeUpdated;
    }

    /**
     * Check if two ad objects represents the same ad
     *
     * @param o Another ad object
     * @return true if two book objects represent the same book.
     */
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

    /**
     * @return a haschode for the object
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, userId, isbn, price, condition, archived);
    }

    public interface GetAdsCallback {
        void onGetAdsComplete(ArrayList<Ad> adList);
    }
}

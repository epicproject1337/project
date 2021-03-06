package com.example.boket.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.boket.controller.Search;
import com.example.boket.controller.integrations.Algolia;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * @author Pajam Khoshnam
 *
 * An object representing a book.
 * Handles all the database adds/updates/gets for books.
 *
 * @since 2020-09-10
 */
public class Book {

    private String isbn;
    private String name;
    private String author;
    private String edition;
    private String releaseYear;
    private String image;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String collection = "books";

    private static final String TAG = Book.class.getName();

    public Book() {
    }

    /**
     * Load a book object from database with the ISBN-number
     * @param isbn book ISBN number
     * @param onLoadCallback callback method that will handle the book object when it is loaded.
     */
    public Book(String isbn, OnLoadCallback onLoadCallback) {
        DocumentReference docRef = db.collection(collection).document(isbn);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    Book book = documentSnapshot.toObject(Book.class);
                    onLoadCallback.onLoadComplete(book);
                    Log.d(TAG, "SUCCESS:" + book.toString());
                } else {
                    onLoadCallback.onLoadComplete(null);
                }
            }
        });
    }

    /**
     * Create a book object with all the necessary fields.
     * @param isbn book ISBN
     * @param name Name of the book
     * @param author Author/s of the book
     * @param edition Edition of the book
     * @param releaseYear The year which the book was released
     * @param image URL link to an image of the book.
     */
    public Book(String isbn, String name, String author, String edition, String releaseYear, String image) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.edition = edition;
        this.releaseYear = releaseYear;
        this.image = image;
    }

    /**
     * @return book isbn number
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @return book name
     */
    public String getName() {
        return name;
    }

    /**
     * @return book author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return book edition
     */
    public String getEdition() {
        return edition;
    }

    /**
     * @return book release year
     */
    public String getReleaseYear() {
        return releaseYear;
    }

    /**
     * @return url link to an image of the book
     */
    public String getImage() {
        return image;
    }

    /**
     * Saves the current book object to the database. If there is already a book with the same isbn
     * in the database it will update that book.
     * it will also add the book (or update) to the Algolia search engine.
     */
    public void save() {
        //TODO : Add validation to make sure 1. all fields are set and valid, 2. There is no excisting book with same ISBN
        db.collection(collection).document(isbn).set(this).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

        //Add to search index..
        try {
            JSONObject jObj = new JSONObject().put("objectID", this.isbn )
                    .put("isbn", this.isbn)
                    .put("name", this.name)
                    .put("author", this.author);
            Search.addToIndex(jObj);
        } catch (JSONException e) {
            //TODO: Error handling
            e.printStackTrace();
        }

    }

    /**
     * Check if two book objects represents the same book
     * @param o Another book object
     * @return true if two book objects represent the same book.
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn) &&
                Objects.equals(name, book.name) &&
                Objects.equals(author, book.author) &&
                Objects.equals(edition, book.edition) &&
                Objects.equals(releaseYear, book.releaseYear) &&
                Objects.equals(image, book.image) &&
                Objects.equals(collection, book.collection);
    }

    /**
     * @return a haschode for the object
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(isbn, name, author, edition, releaseYear, image, collection);
    }

    /**
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", edition='" + edition + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", image='" + image + '\'' +
                ", collection='" + collection + '\'' +
                '}';
    }

    public interface OnLoadCallback{
        void onLoadComplete(Book book);
    }

}

package com.example.boket.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.boket.model.integrations.Algolia;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Book {

    private String isbn;
    private String name;
    //TODO: Author should be some kind of array/object.
    private String author;
    private String edition;
    private String releaseYear;
    private String image;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String collection = "books";

    private static final String TAG = Book.class.getName();
    private static final String bookIndex = "BOOKINDEX";

    public Book() {
    }

    public Book(String isbn, OnLoadCallback onLoadCallback) {
        DocumentReference docRef = db.collection(collection).document(isbn);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Book book = documentSnapshot.toObject(Book.class);
                onLoadCallback.onLoadComplete(book);
                Log.d(TAG, "SUCCESS:" + book.toString());
            }
        });

    }

    public Book(String isbn, String name, String author, String edition, String releaseYear, String image) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.edition = edition;
        this.releaseYear = releaseYear;
        this.image = image;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getEdition() {
        return edition;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getImage() {
        return image;
    }

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

        //Add to Algolia..
        try {
            JSONObject jObj = new JSONObject().put("objectID", this.isbn )
                    .put("isbn", this.isbn)
                    .put("name", this.name)
                    .put("author", this.author);
            Algolia a = new Algolia(bookIndex);
            a.addToIndex(jObj);
        } catch (JSONException e) {
            //TODO: Error handling
            e.printStackTrace();
        }

    }

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(isbn, name, author, edition, releaseYear, image, collection);
    }

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

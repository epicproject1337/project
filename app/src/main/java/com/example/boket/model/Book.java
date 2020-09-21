package com.example.boket.model;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public Book() {
    }

    public Book(String isbn) {
        DocumentReference docRef = db.collection(collection).document(isbn);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Book book = documentSnapshot.toObject(Book.class);
                loadData(book);
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

    public void create() {
        //TODO : Add validation to make sure 1. all fields are set and valid, 2. There is no excisting book with same ISBN
        db.collection(collection).document(isbn).set(this).getResult();
    }

    private void loadData(Book book) {
        this.isbn = book.getIsbn();
        this.name = book.getName();
        this.author = book.getAuthor();
        this.edition = book.getEdition();
        this.releaseYear = book.getReleaseYear();
        this.image = book.getImage();
    }
}

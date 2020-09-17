package model;

import androidx.test.core.app.ApplicationProvider;
import android.content.Context;

import com.example.boket.model.Book;
import com.google.firebase.FirebaseApp;

import org.junit.Test;

import static org.junit.Assert.*;


public class BookTest {
    private Context context = ApplicationProvider.getApplicationContext();


    @Test
    public void Book_CreateBook(){
        FirebaseApp.initializeApp(context);
        Book book = new Book("9789144090504", "Algebra och diskret matematik", "Johan Jonasson, Stefan Lemurell", "2", "2013", "noimage");
        book.create();
        Book newBook = new Book("9789144090504");
        assertEquals(book, newBook);
    }

}

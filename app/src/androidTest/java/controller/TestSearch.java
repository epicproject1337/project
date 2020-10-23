package controller;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import com.example.boket.model.Book;
import com.example.boket.controller.Search;
import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestSearch {

    private Context context;

    @Before
    public void init(){
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void TestSearching() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseApp.initializeApp(context);
        Search.searchBooks("Algebra", new Search.SearchCallback() {
            @Override
            public void onSearchBooks(ArrayList<Book> bookList) {
                Book loadedBook = new Book("9789144060545", new Book.OnLoadCallback() {
                    @Override
                    public void onLoadComplete(Book book) {
                        Log.d("TESTING", "SUCCESS:" + book.toString());
                        assertEquals(bookList.contains(book), true);
                        lock.countDown();
                    }
                });
            }
        });
        lock.await(1, TimeUnit.MINUTES);
    }

}

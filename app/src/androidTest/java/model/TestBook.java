package model;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import com.example.boket.model.Book;
import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestBook {

    private Context context;

    @Before
    public void init(){
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void Book_CreateAndGetBook() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseApp.initializeApp(context);
        Book book1 = new Book("9789144090504", "Algebra och diskret matematik", "Johan Jonasson, Stefan Lemurell", "2", "2013", "https://s1.adlibris.com/images/3075059/algebra-och-diskret-matematik.jpg");
        book1.save();
        Book newBook = new Book("9789144090504", new Book.OnLoadCallback() {
            @Override
            public void onLoadComplete(Book book) {
                Log.d("BAJSB", "SUCCESS:" + book.toString());
                assertEquals(book1, book);
                lock.countDown();
            }
        });
        lock.await(1, TimeUnit.MINUTES);
    }

}

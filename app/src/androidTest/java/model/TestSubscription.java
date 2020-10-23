package model;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import com.example.boket.model.Book;
import com.example.boket.model.Subscription;
import com.example.boket.model.user.User;
import com.google.firebase.FirebaseApp;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestSubscription {

    private Context context;

    @Before
    public void init(){
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void TestSubscribingUserToBook() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseApp.initializeApp(context);

        User user = new MockUser();
        Subscription.subscribeUser("9789144090504", user.getUid());

        Subscription.isSubscribed("9789144090504", user.getUid(), new Subscription.OnLoadCallback() {
            @Override
            public void isSubscribedCallback(boolean isSubscribed) {
                assertEquals(isSubscribed, true);
                lock.countDown();
            }
        });
        lock.await(1, TimeUnit.MINUTES);
    }

    @Test
    public void TestUnsubscribingUserToBook() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseApp.initializeApp(context);

        User user = new MockUser();
        Subscription.subscribeUser("9789144090504", user.getUid());
        Subscription.unsubscribeUser("9789144090504", user.getUid());

        Subscription.isSubscribed("9789144090504", user.getUid(), new Subscription.OnLoadCallback() {
            @Override
            public void isSubscribedCallback(boolean isSubscribed) {
                assertEquals(isSubscribed, false);
                lock.countDown();
            }
        });
        lock.await(1, TimeUnit.MINUTES);
    }

    @Test
    public void TestGettingSubscribedBooks() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseApp.initializeApp(context);

        User user = new MockUser();
        Subscription.subscribeUser("9789144090504", user.getUid());

        Subscription.getSubscribedBooks(user.getUid(), new Subscription.OnLoadSubscribedBooksCallback() {
            @Override
            public void onCompleteCallback(ArrayList<Book> books) {
                Book book = new Book("9789144090504", new Book.OnLoadCallback() {
                    @Override
                    public void onLoadComplete(Book book) {
                        assertEquals(books.contains(book), true);
                        lock.countDown();
                    }
                });
            }
        });
        lock.await(1, TimeUnit.MINUTES);
    }

}

package ui.booksellerTest;

import android.os.Bundle;
import android.os.LocaleList;
import android.widget.Button;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Book;
import com.example.boket.model.Subscription;
import com.example.boket.model.user.LocalUser;
import com.example.boket.ui.bookSeller.BooksellersFragment;
import com.example.boket.ui.profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class BooksellersFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    private MainActivity mActivity = null;
    private BooksellersFragment booksellersFragment = null;
    private LocalUser user = LocalUser.getCurrentUser();
    private ViewInteraction subscribeBtn;


    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        booksellersFragment = new BooksellersFragment();

        Bundle bundle = new Bundle();
        bundle.putString("BookNumber", DB_ISBN_generator());
        booksellersFragment.setArguments(bundle);
        startFragment(booksellersFragment);
        subscribeBtn = onView(withId(R.id.subscribeButton));


    }

    private void startFragment(Fragment fragment) {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }
/*
    @Test
    public void testExpandableView() {
        RecyclerView rv = onView(withId(R.id.adList));
        onView(ViewMatchers.withId(R.id.adList))
                .

            Espresso.onView(ViewMatchers.withId(R.id.adList))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
    }


 */
    @Test
    public void testSubscribeButtonClicked() throws InterruptedException {
        Bundle bundle = booksellersFragment.getArguments();
        assert bundle != null;
        String bookISBN = bundle.getString("BookNumber");
        boolean subscriptionChanged = Subscribed(bookISBN);

        subscribeBtn.perform(click());

        assertEquals(!subscriptionChanged, Subscribed(bookISBN));
    }

    private boolean Subscribed(String bookISBN) throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        final boolean[] subscribed = new boolean[1];

        Subscription.isSubscribed(bookISBN, user.getUid(), new Subscription.OnLoadCallback() {

            @Override
            public void isSubscribedCallback(boolean isSubscribed) {
                subscribed[0] = isSubscribed;
                lock.countDown();
            }
        });

        lock.await(2, TimeUnit.MINUTES);
        //Thread.sleep(1500);
        return subscribed[0];
    }

    @Test
    public void testRecyclerViewIsVisible() throws InterruptedException {

        onView(withId(R.id.adList)).check(matches(isDisplayed()));
    }

    @Test
    public void testSubscribeBtnIsVisible() {
        onView(withId(R.id.subscribeButton)).check(matches(isDisplayed()));
    }


    @Test
    public void testBookNameIsVisible() {
        onView(withId(R.id.bookName)).check(matches(isDisplayed()));
    }

    @Test
    public void testBookImagesIsVisible() {
        onView(withId(R.id.bookImage)).check(matches(isDisplayed()));
    }

    private String DB_ISBN_generator() {
        String[] ISBNarr = {"9780134154367", "9781492051961", "9789144038698", "9789144060545",
                "9789144067650", "9789144076508", "9789144090504",
                "9789144115610", "9789144117232", "9789144127408"};
        Random rand = new Random();
        int i = rand.nextInt(10);
        return ISBNarr[i];
    }


    @After
    public void tearDown() throws Exception {
        removeFragment(booksellersFragment);

    }

    public void removeFragment(Fragment fragment) {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
}
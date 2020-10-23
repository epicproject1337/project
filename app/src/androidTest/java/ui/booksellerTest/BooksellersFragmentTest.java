package ui.booksellerTest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.bumptech.glide.Glide;
import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Ad;
import com.example.boket.model.Book;
import com.example.boket.model.Subscription;
import com.example.boket.model.user.LocalUser;
import com.example.boket.ui.bookSeller.ABookSellerHolder;
import com.example.boket.ui.bookSeller.BooksellersFragment;
import com.example.boket.ui.bookSeller.IABookSellerCL;
import com.example.boket.ui.profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class BooksellersFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    /*
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);


     */
    private MainActivity mActivity = null;
    private BooksellersFragment booksellersFragment = null;
    private LocalUser user = LocalUser.getCurrentUser();
    private ViewInteraction subscribeBtn;


    @Before
    public void setUp() throws InterruptedException {
        mActivity = mActivityTestRule.getActivity();
        booksellersFragment = new BooksellersFragment();

        Bundle bundle = new Bundle();
        bundle.putString("isbn", "9789144090504");//DB_ISBN_generator()
        booksellersFragment.setArguments(bundle);
        startFragment(booksellersFragment);
        subscribeBtn = onView(withId(R.id.subscribeButton));


    }

    private void startFragment(Fragment fragment) throws InterruptedException {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();

    }

    @Test
    public void testHoldersRightAmount() throws InterruptedException {
        Thread.sleep(500);
        RecyclerView rv = mActivity.findViewById(R.id.adList);
        int holders = rv.getAdapter().getItemCount();
        final int[] adListSize = new int[1];
        Ad.getAdsByISBN(booksellersFragment.getArguments().getString("isbn"), new Ad.GetAdsCallback() {
            @Override
            public void onGetAdsComplete(ArrayList<Ad> adList) {
                adListSize[0] = adList.size();

            }
        });
        Thread.sleep(500);
        assertEquals(holders, adListSize[0]);
    }

    @Test
    public void testExpandableView() throws InterruptedException {
        Thread.sleep(500);
        RecyclerView rv = mActivity.findViewById(R.id.adList);

        int holders = rv.getAdapter().getItemCount();
        if (holders == 0) {
            assertTrue(true);
            return;
        }

        int randomPos = randomInt(holders);

        onView(ViewMatchers.withId(R.id.adList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(randomPos,
                        click()));

        Thread.sleep(100);
        ABookSellerHolder vh = (ABookSellerHolder) rv.findViewHolderForLayoutPosition(randomPos);
        int visibilityCode = 0; //if it is visible it returns 0 (java class)
        assertEquals(vh.getExpandableLayout().getVisibility(), visibilityCode);
    }

    @Test
    public void testGmailOpens() throws InterruptedException {


        RecyclerView rv = mActivity.findViewById(R.id.adList);
        //Thread.sleep(3000);
        if (rv.getAdapter().getItemCount() == 0) {
            assertTrue(false);
            return;
        }

        int randomPos = randomInt(rv.getAdapter().getItemCount());

        onView(ViewMatchers.withId(R.id.adList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(randomPos,
                        click()));

        ABookSellerHolder vh = (ABookSellerHolder) rv.findViewHolderForAdapterPosition(randomPos);

        // contactButton.performClick();
        intended(toPackage(("com.google.android.gm")));

    }


    @Test
    public void testTextSubscribeBtn() throws InterruptedException {
        Thread.sleep(500);
        Bundle bundle = booksellersFragment.getArguments();
        assert bundle != null;
        String bookISBN = bundle.getString("isbn");
        boolean subscribed = Subscribed(bookISBN);
        if (!subscribed) {
            onView(withId(R.id.subscribeButton)).check(matches(withText("PRENUMERERA")));
        } else {
            onView(withId(R.id.subscribeButton)).check(matches(withText("AVPRENUMERERA")));
        }
    }

    @Test
    public void testTextSubscribeBtnClicked() throws InterruptedException {
        Bundle bundle = booksellersFragment.getArguments();
        assert bundle != null;
        String bookISBN = bundle.getString("isbn");
        boolean subscriptionChanged = Subscribed(bookISBN);
        if (!subscriptionChanged) {
            onView(withId(R.id.subscribeButton)).check(matches(withText("PRENUMERERA")));
            onView(withId(R.id.subscribeButton)).perform(click());
            onView(withId(R.id.subscribeButton)).check(matches(withText("AVPRENUMERERA")));
        } else {
            onView(withId(R.id.subscribeButton)).check(matches(withText("AVPRENUMERERA")));
            onView(withId(R.id.subscribeButton)).perform(click());
            onView(withId(R.id.subscribeButton)).check(matches(withText("PRENUMERERA")));
        }
    }

    @Test
    public void testSubscribeButtonClicked() throws InterruptedException {
        Bundle bundle = booksellersFragment.getArguments();
        assert bundle != null;
        String bookISBN = bundle.getString("isbn");
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

        lock.await(1, TimeUnit.MINUTES);
        Thread.sleep(500);
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
    public void testCorrectBookName() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        assert booksellersFragment.getArguments() != null;
        String isbn = booksellersFragment.getArguments().getString("isbn");
        final String[] bookName = new String[1];
        Book book = new Book(isbn, new Book.OnLoadCallback() {
            @Override
            public void onLoadComplete(Book book) {
                bookName[0] = book.getName();
                lock.countDown();
            }
        });
        lock.await(1, TimeUnit.MINUTES);
        TextView tv = mActivity.findViewById(R.id.bookName);
        Thread.sleep(50);
        String bookNameOnScreen = tv.getText().toString();

        assertEquals(bookName[0], bookNameOnScreen);
    }

    @Test
    public void testBookImagesIsVisible() {
        onView(withId(R.id.bookImage)).check(matches(isDisplayed()));
    }

    private String DB_ISBN_generator() {
        String[] ISBNarr = {"9780134154367", "9781492051961", "9789144038698", "9789144060545",
                "9789144067650", "9789144076508", "9789144090504",
                "9789144115610", "9789144117232", "9789144127408"};

        int i = randomInt(10);
        return ISBNarr[i];
    }

    private int randomInt(int i) {
        Random rand = new Random();
        int randomPos = rand.nextInt(i);
        return randomPos;
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

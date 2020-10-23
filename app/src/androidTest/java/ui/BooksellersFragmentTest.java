package ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Ad;
import com.example.boket.model.Book;
import com.example.boket.model.Subscription;
import com.example.boket.model.user.LocalUser;
import com.example.boket.ui.bookSeller.ABookSellerHolder;
import com.example.boket.ui.bookSeller.BooksellersFragment;

import org.hamcrest.Matcher;
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
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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
    public void setUp() throws InterruptedException {
        mActivity = mActivityTestRule.getActivity();
        booksellersFragment = new BooksellersFragment();

        Bundle bundle = new Bundle();
        bundle.putString("isbn", "9789144090504");//"9789144090504"disk "9789144115610"  DB_ISBN_generator()
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
        Thread.sleep(1000);
        RecyclerView rv = mActivity.findViewById(R.id.adList);
        Thread.sleep(1000);
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
    public void testCorrectHolder() throws InterruptedException {
        Thread.sleep(1000);
        RecyclerView rv = mActivity.findViewById(R.id.adList);
        Thread.sleep(1000);
        int holders = rv.getAdapter().getItemCount();
        if (holders == 0) {
            assertTrue(true);
            return;
        }
        int randomPos = randomInt(holders);

        onView(ViewMatchers.withId(R.id.adList))
                .perform(RecyclerViewActions.scrollToPosition(randomPos));

        Thread.sleep(2000);
        ABookSellerHolder aBookSellerHolder = (ABookSellerHolder) rv.findViewHolderForAdapterPosition(randomPos);
        Thread.sleep(2000);
        boolean correct = checkBookHolder(aBookSellerHolder, randomPos);
        assertTrue(correct);
    }

    private boolean checkBookHolder(ABookSellerHolder aBookSellerHolder, int pos) throws InterruptedException {
        String isbn = booksellersFragment.getArguments().getString("isbn");
        MockListenerAd mockListener = new MockListenerAd();
        Ad.getAdsByISBN(isbn, mockListener);
        synchronized (mockListener) {
            mockListener.wait(10000);
        }
        ArrayList<Ad> adArr = mockListener.getAdArr();


        Ad ad = adArr.get(pos);
        if (!aBookSellerHolder.getCity().getText().toString().equals(ad.getCity())) {
            return false;
        }
        if (!aBookSellerHolder.getPrice().getText().toString().equals(ad.getPrice() + " kr")) {
            return false;
        }

        if (!aBookSellerHolder.getState().getText().toString().equals(ad.getCondition())) {
            return false;
        }

        return true;

    }


    @Test
    public void testExpandableView() throws InterruptedException {
        Thread.sleep(1000);
        RecyclerView rv = mActivity.findViewById(R.id.adList);
        Thread.sleep(1000);
        int holders = rv.getAdapter().getItemCount();
        if (holders == 0) {
            assertTrue(true);
            return;
        }
        int randomPos = 0;
        Thread.sleep(1000);
        onView(ViewMatchers.withId(R.id.adList))
                .perform(RecyclerViewActions.scrollToPosition(randomPos));
        Thread.sleep(1000);
            onView(ViewMatchers.withId(R.id.adList))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(randomPos,
                            click()));
        Thread.sleep(1000);
        ABookSellerHolder vh = (ABookSellerHolder) rv.findViewHolderForAdapterPosition(randomPos);
        Thread.sleep(2000);
        int visibilityCode = 0; //if it is visible it returns 0 (java class)
        assertEquals(vh.getExpandableLayout().getVisibility(), visibilityCode);
    }

    /**
     * Problem
     *
     * @throws InterruptedException
     */
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
        Thread.sleep(1500);
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
        Thread.sleep(1000);
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
        Thread.sleep(1000);
        boolean subscriptionChanged = Subscribed(bookISBN);
        Thread.sleep(1000);

        subscribeBtn.perform(click());
        Thread.sleep(1000);
        assertEquals(!subscriptionChanged, Subscribed(bookISBN));
    }

    private boolean Subscribed(String bookISBN) throws InterruptedException {
        MockListenerSubscribe mockListener = new MockListenerSubscribe();
        Subscription.isSubscribed(bookISBN, user.getUid(), mockListener);
        synchronized (mockListener) {
            mockListener.wait(10000);
        }
        return mockListener.isSubscribed();
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
        Thread.sleep(500);
        String bookNameOnScreen = tv.getText().toString();

        assertEquals(bookName[0], bookNameOnScreen);
    }

    public static ViewAction doTaskInUIThread(final Runnable r) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                r.run();
            }
        };
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
        //removeFragment(booksellersFragment);
    }

    public void removeFragment(Fragment fragment) {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    class MockListenerSubscribe implements Subscription.OnLoadCallback {
        boolean subscribed;

        @Override
        public void isSubscribedCallback(boolean isSubscribed) {
            subscribed = isSubscribed;
            synchronized (this) {
                notifyAll();
            }
        }

        public boolean isSubscribed() {
            return subscribed;
        }
    }

    class MockListenerAd implements Ad.GetAdsCallback {
        ArrayList<Ad> adArr = new ArrayList<>();

        @Override
        public void onGetAdsComplete(ArrayList<Ad> adList) {
            for (Ad ad : adList) {
                adArr.add(ad);
            }
            synchronized (this) {
                notifyAll();
            }
        }

        private void sortCheapestFirst() {
            for (int i = 0; i < adArr.size() - 1; i++) {
                for (int j = 0; j < adArr.size() - 1 - i; j++) {
                    Ad ad = adArr.get(j);
                    Ad ad2 = adArr.get(j + 1);
                    if (ad.getPrice() > ad2.getPrice()) {
                        Ad tmp = ad;
                        adArr.set(j, ad2);
                        adArr.set(j + 1, tmp);
                    }
                }
            }
        }

        public ArrayList<Ad> getAdArr() {
            sortCheapestFirst();
            return adArr;
        }

    }

}

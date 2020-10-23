package ui;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Ad;
import com.example.boket.model.Book;
import com.example.boket.model.Subscription;
import com.example.boket.model.user.LocalUser;
import com.example.boket.ui.bookSeller.BooksellersFragment;

import com.example.boket.ui.profile.ProfileFragment;
import com.example.boket.ui.profile.SubscribedBookAdapter;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;
    ProfileFragment profileFragment;
    /*RecyclerView subscribedBooksView;
    RecyclerView adsView;*/


    private void startProfileFragment() {
        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.nav_host_fragment, profileFragment);
        fragmentTransaction.commit();
    }

    @Before
    public void setup(){
        mainActivity = mainActivityTestRule.getActivity();
        profileFragment = new ProfileFragment();
        startProfileFragment();
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
    public void testSubscribedBooksViewIsVisible(){
        onView(withId(R.id.subscribedBooksView)).check(matches(isDisplayed()));
    }

    @Test
    public void testAdsViewIsVisible(){
        onView(withId(R.id.adsView)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignOutButton() {
        /*assertTrue(LocalUser.getCurrentUser() != null);
        onView(withId(R.id.signOutButton)).perform(click());
        assertTrue(LocalUser.getCurrentUser() == null);*/
    }

    @Test
    public void  testNumberOfSubscribedBooks() throws InterruptedException {
        // Make sure that recyclerview is filled with data
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SubscribedMockListener mockListener = new SubscribedMockListener();
        Subscription.getSubscribedBooks(LocalUser.getCurrentUser().getUid(), mockListener);
        synchronized (mockListener) {
            mockListener.wait(10000);
        }

        ArrayList<Book> books = mockListener.getBooks();
        assertNotNull("Timed oud", books);

        ViewAssertion v = new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (!(view instanceof RecyclerView)) {
                    throw noViewFoundException;
                }
                RecyclerView rv = (RecyclerView) view;
                int expected = books.size();
                int itemCount = rv.getAdapter().getItemCount();
                assertEquals(expected, itemCount);

            }
        };
        onView(withId(R.id.subscribedBooksView)).check(v);
    }

    @Test
    public void  testNumberOfAds() throws InterruptedException {
        // Make sure that recyclerview is filled with data
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AdsMockListener mockListener = new AdsMockListener();
        Ad.getAdsByUser(LocalUser.getCurrentUser().getUid(), false, mockListener);
        synchronized (mockListener) {
            mockListener.wait(10000);
        }

        ArrayList<Ad> ads = mockListener.getAds();
        assertNotNull("Timed oud", ads);

        ViewAssertion v = new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (!(view instanceof RecyclerView)) {
                    throw noViewFoundException;
                }
                RecyclerView rv = (RecyclerView) view;

                int itemCount = rv.getAdapter().getItemCount();
                assertEquals(ads.size(), itemCount);
            }
        };
        onView(withId(R.id.adsView)).check(v);
    }

    @Test
    public void testNameText() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String expected = LocalUser.getCurrentUser().getName().split(" ", 2)[0].concat("'s profil");
        onView(withId(R.id.profileName)).check(matches(withText(expected)));
    }

    class SubscribedMockListener implements Subscription.OnLoadSubscribedBooksCallback {
        ArrayList<Book> books;
        @Override
        public void onCompleteCallback(ArrayList<Book> books) {
            this.books = books;
            synchronized (this) {
                notifyAll();
            }
        }

        public ArrayList<Book> getBooks() {
            return books;
        }
    }

    class AdsMockListener implements Ad.GetAdsCallback {
        ArrayList<Ad> ads;

        public ArrayList<Ad> getAds() {
            return ads;
        }

        @Override
        public void onGetAdsComplete(ArrayList<Ad> adList) {
            this.ads = adList;
            synchronized (this) {
                notifyAll();
            }
        }
    }

}

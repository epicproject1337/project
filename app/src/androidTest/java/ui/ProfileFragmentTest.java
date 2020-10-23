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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
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

    private int getSubscribedBooks() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        final int[] expected = new int[1];
        Subscription.getSubscribedBooks(LocalUser.getCurrentUser().getUid(), new Subscription.OnLoadSubscribedBooksCallback() {
            @Override
            public void onCompleteCallback(ArrayList<Book> books) {
                expected[0] = books.size();
                lock.countDown();
            }
        });
        lock.await(1, TimeUnit.MINUTES);
        return expected[0];
    }

    @Test
    public void  testAmountOfListElements() {
        ViewAssertion v = new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                if (!(view instanceof RecyclerView)) {
                    throw noViewFoundException;
                }
                RecyclerView rv = (RecyclerView) view;
                try {
                    int expected = getSubscribedBooks();
                    int itemCount = rv.getAdapter().getItemCount();
                    assertEquals(expected, itemCount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        onView(withId(R.id.subscribedBooksView)).check(v);
    }

}

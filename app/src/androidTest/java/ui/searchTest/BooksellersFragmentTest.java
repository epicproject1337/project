package ui.searchTest;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Book;
import com.example.boket.ui.search.ABookSeller;
import com.example.boket.ui.search.BooksellersFragment;
import com.example.boket.ui.search.SearchFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import static ui.SearchFragmentTest.doTaskInUIThread;

@RunWith(AndroidJUnit4.class)
public class BooksellersFragmentTest {

    //BooksellersFragment booksellersFragment;

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);


    private void startBooksellersFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("BookNumber", "9789144090504");

        BooksellersFragment booksellersFragment = new BooksellersFragment();
        booksellersFragment.setArguments(bundle);
        transaction.add(booksellersFragment, "booksellersFragemnt");

        transaction.commit();

    }


    @Before
    public void setup() {
        onView(isRoot()).perform(doTaskInUIThread(new Runnable() {
            @Override
            public void run() {
                startBooksellersFragment();
            }
        }));

    }

/*

    @Test
    public void test_BookNameIsVisible() {
        onView(withId(R.id.bookName)).check(matches(withText("hej")));
    }

  */

    @Test
    public void test_SubscribeBtnIsVisible() {
        onView(withId(R.id.subscribeButton)).check(matches(isDisplayed()));
    }


/*

    @Test
    public void test_RecyclerViewIsVisible() {
        onView(withId(R.id.adList)).check(matches(isDisplayed()));
    }

    @Test
    public void onCreateView() {
    }

    @Test
    public void onIABookSellerCL() {
    }

    @Test
    public void onCreate() {
    }


 */

}

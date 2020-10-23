package ui;

import android.view.View;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Book;
import com.example.boket.ui.search.BookItem;
import com.example.boket.ui.search.BookItemAdapter;
import com.example.boket.ui.search.SearchFragment;
import com.example.boket.ui.search.SearchViewModel;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;


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
public class SearchFragmentTest {

    SearchFragment searchFragment;
    SearchViewModel searchViewModel;
    RecyclerView recyclerView;

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);

    private void startSearchFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        searchFragment = new SearchFragment();
        transaction.add(searchFragment, "searchFragment");
        transaction.commit();
    }

    @Before
    public void setup() {
        onView(isRoot()).perform(doTaskInUIThread(new Runnable() {
            @Override
            public void run() {
                //Code to add your fragment or anytask that you want to do from UI Thread
                startSearchFragment();

            }
        }));
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
    public void testGetBooks() throws Exception {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                //Task that need to be done in UI Thread (below I am adding a fragment)
                searchViewModel = ViewModelProviders.of(searchFragment).get(SearchViewModel.class);

                ArrayList<Book> books = new ArrayList<>();
                Book book = new Book("9789144090504", "Algebra och diskret matematik", "Johan Jonasson, Stefan Lemurell", "2", "2013", "https://s1.adlibris.com/images/3075059/algebra-och-diskret-matematik.jpg");
                books.add(book);
                searchViewModel.setBooks(books);

                assertEquals(1, searchViewModel.getBooks().size());
            }
        };
        onView(isRoot()).perform(doTaskInUIThread(r));

    }

    @Test
    public void testRecyclerViewIsVisible() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testBookSearch() throws Exception {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                searchFragment.onQueryTextSubmit("Algebra");
            }
        };
        onView(isRoot()).perform(doTaskInUIThread(r));

        //Sleep thread so searched book can be added to recycler view before the test runs
        try {
            Thread.sleep(5 * 1000);

            Runnable r2 = new Runnable() {
                @Override
                public void run() {
                    searchViewModel = ViewModelProviders.of(searchFragment).get(SearchViewModel.class);

                    //List of books is empty before search, list size larger than 1 show that search was successful
                    assertTrue(searchViewModel.getBooks().size() > 0);
                }
            };
            onView(isRoot()).perform(doTaskInUIThread(r2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

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
import java.util.Timer;
import java.util.TimerTask;

import static androidx.test.espresso.Espresso.onView;
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
    Timer timer = new Timer();


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
    public void setup(){
        onView(isRoot()).perform(doTaskInUIThread(new Runnable() {
            @Override
            public void run() {
                //Code to add your fragment or anytask that you want to do from UI Thread
                startSearchFragment();
            }
        }));

        fillRecyclerView(searchFragment, this);
    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }

    @Test
    public void testRecyclerViewContainsItem(){


                assertEquals(1, recyclerView.getChildCount());

    }


    public static ViewAction fillRecyclerView(SearchFragment searchFragment, SearchFragmentTest searchFragmentTest) {
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
                BookItem bookItem = new BookItem(searchFragment.getContext(), "s", "s", "s", "s", "s", "s");
                ArrayList<BookItem> bookItems = new ArrayList<>();
                bookItems.add(bookItem);
                RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
                BookItemAdapter bookItemAdapter = new BookItemAdapter(searchFragment.getContext(), searchFragment, bookItems);
                recyclerView.setAdapter(bookItemAdapter);
                searchFragmentTest.setRecyclerView(recyclerView);

            }
        };

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
    public void testGetBooks() throws Exception{

        Runnable r = new Runnable() {
            @Override
            public void run() {
                //Task that need to be done in UI Thread (below I am adding a fragment)

                searchViewModel = ViewModelProviders.of(searchFragment).get(SearchViewModel.class);

                ArrayList<Book> books = new ArrayList<>();
                Book book = new Book("s", "s", "s", "s", "s", "s");
                books.add(book);
                searchViewModel.setBooks(books);

                assertEquals(1, searchViewModel.getBooks().size());

            }
        };
        onView(isRoot()).perform(doTaskInUIThread(r));

    }



    @Test
    public void testRecyclerViewIsVisible(){
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }


    @Test
    public void testOnBookSearch() throws Exception{


        Runnable r = new Runnable() {
            @Override
            public void run() {

                searchFragment.onQueryTextSubmit("Algebra");

                searchViewModel = ViewModelProviders.of(searchFragment).get(SearchViewModel.class);
                //Task that need to be done in UI Thread

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                assertTrue(searchViewModel.getBooks().size() > 0);


            }
        };
        onView(isRoot()).perform(doTaskInUIThread(r));

    }




}

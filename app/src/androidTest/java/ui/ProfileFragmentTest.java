package ui;

import android.view.View;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Ad;
import com.example.boket.model.Book;
import com.example.boket.ui.profile.ManageAdAdapter;
import com.example.boket.ui.profile.SubscribedBookAdapter;
import com.example.boket.ui.search.BookItemAdapter;
import com.example.boket.ui.profile.ProfileFragment;
import com.example.boket.ui.search.SearchViewModel;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Timer;

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
public class ProfileFragmentTest {

    ProfileFragment profileFragment;
    SearchViewModel searchViewModel;
    RecyclerView subscribedBooksView;
    RecyclerView adsView;
    Timer timer = new Timer();


    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);


    private void startProfileFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        profileFragment = new ProfileFragment();
        transaction.add(profileFragment, "profileFragment");
        transaction.commit();
    }

    @Before
    public void setup(){
        onView(isRoot()).perform(doTaskInUIThread(new Runnable() {
            @Override
            public void run() {
                //Code to add your fragment or anytask that you want to do from UI Thread
                startProfileFragment();
            }
        }));

        ViewAction va = fillRecyclerViews(profileFragment, this);
    }

    public void setSubscribedBooksView(RecyclerView subscribedBooksView){
        this.subscribedBooksView = subscribedBooksView;
    }

    public void setAdsView(RecyclerView adsView){
        this.adsView = adsView;
    }

    @Test
    public void testSubscribedBooksViewContainsItem(){
        assertEquals(1, subscribedBooksView.getChildCount());
    }

    @Test
    public void testAdsViewContainsItem(){
        assertEquals(1, adsView.getChildCount());
    }


    public static ViewAction fillRecyclerViews(ProfileFragment profileFragment, ProfileFragmentTest profileFragmentTest) {
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
                // Fill subscribed books recyclerview
                Book book = new Book("123", "Testbook", "Testauthor", "1", "2019", "Image");
                ArrayList<Book> books = new ArrayList<>();
                books.add(book);
                RecyclerView subscribedBooksView = view.findViewById(R.id.subscribedBooksView);
                subscribedBooksView.setHasFixedSize(true);
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
                SubscribedBookAdapter subscribedBookAdapter = new SubscribedBookAdapter(profileFragment.getContext(), profileFragment, books);
                subscribedBooksView.setLayoutManager(layoutManager);
                subscribedBooksView.setAdapter(subscribedBookAdapter);
                profileFragmentTest.setSubscribedBooksView(subscribedBooksView);

                // Fill ads recyclerview
                FirebaseAuth auth = FirebaseAuth.getInstance();
                Ad ad = new Ad(auth.getUid(), "test@mail.se", "12345", 100, "Great condition", "Gothenburg", false);
                ArrayList<Ad> ads = new ArrayList<>();
                ads.add(ad);
                RecyclerView adsView = view.findViewById(R.id.adsView);
                adsView.setHasFixedSize(true);
                LinearLayoutManager layoutManager2
                        = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
                adsView.setLayoutManager(layoutManager2);
                ManageAdAdapter adAdapter = new ManageAdAdapter(profileFragment.getContext(), profileFragment, ads);
                adsView.setAdapter(adAdapter);
                profileFragmentTest.setAdsView(adsView);

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
    public void testGetSubscribedBooks() throws Exception{

        Runnable r = new Runnable() {
            @Override
            public void run() {
                //Task that need to be done in UI Thread (below I am adding a fragment)

                /*searchViewModel = ViewModelProviders.of(profileFragment).get(SearchViewModel.class);

                ArrayList<Book> books = new ArrayList<>();
                Book book = new Book("s", "s", "s", "s", "s", "s");
                books.add(book);
                searchViewModel.setBooks(books);

                assertEquals(1, searchViewModel.getBooks().size());*/

            }
        };
        onView(isRoot()).perform(doTaskInUIThread(r));

    }

    @Test
    public void testSubscribedBooksViewIsVisible(){
        onView(withId(R.id.subscribedBooksView)).check(matches(isDisplayed()));
    }

    @Test
    public void testAdsViewIsVisible(){
        onView(withId(R.id.adsView)).check(matches(isDisplayed()));
    }

}

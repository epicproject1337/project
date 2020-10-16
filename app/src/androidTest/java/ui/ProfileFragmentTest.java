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

import com.example.boket.ui.profile.ProfileFragment;
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

}

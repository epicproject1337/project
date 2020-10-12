package ui;

import androidx.fragment.app.FragmentTransaction;
import androidx.test.rule.ActivityTestRule;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.ui.search.SearchFragment;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class SearchFragmentTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void fragmentCanBeInstantiated() {
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SearchFragment searchFragment = startSearchFragment();
            }
        });
        // Then use Espresso to test the Fragment
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
    }

    private SearchFragment startSearchFragment() {
        MainActivity activity = (MainActivity) activityRule.getActivity();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        transaction.add(searchFragment, "searchFragment");
        transaction.commit();
        return searchFragment;
    }

}

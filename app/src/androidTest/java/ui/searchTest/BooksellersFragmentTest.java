package ui.searchTest;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.filters.LargeTest;

import com.example.boket.MainActivity;
import com.example.boket.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class BooksellersFragmentTest {

    ViewInteraction subscibeButton;
    ViewInteraction expandableView;

    @Before
    public void setUp() {
        subscibeButton = onView(withId(R.id.contactSellerBtn));
        Intents.init();
    }

    @Test
    public void testsubscribedButtonClicked() {

        subscibeButton.perform(click());

    }
}

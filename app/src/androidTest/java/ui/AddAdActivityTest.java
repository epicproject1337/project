package ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.ui.addAd.AddAdActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddAdActivityTest {

    ViewInteraction conditionInput;
    ViewInteraction priceInput;
    ViewInteraction publishButton;
    //To tell the compiler which activity to run test on
    @Rule
    public ActivityTestRule<AddAdActivity> activityRule =
            new ActivityTestRule<AddAdActivity>(AddAdActivity.class) {

                @Override
                protected Intent getActivityIntent() {

                    Bundle bundle = new Bundle();
                    bundle.putString("isbn","9789144090504");
                    Intent result = new Intent();
                    result.putExtras(bundle);
                    return result;
                }
            };

    @Before
    public void setUp() {
        activityRule.getActivity();
        conditionInput = onView(withId(R.id.conditionInput));
        priceInput = onView(withId(R.id.priceInput));
        publishButton = onView(withId(R.id.publishButton));
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testAddAdActivityIntentToMain() {
        conditionInput.perform(replaceText("SKICK"));
        priceInput.perform(replaceText("123"));
        publishButton.perform(scrollTo()).perform(click());

        intended(hasComponent(MainActivity.class.getName()));

    }

    @Test
    public void testInputHintsWhenButtonPushed() {
        publishButton.perform(scrollTo()).perform(click());
        conditionInput.check(matches(withHint("Skriv beskrivning av skicket")));
        priceInput.check(matches(withHint("Skriv pris")));
    }
}

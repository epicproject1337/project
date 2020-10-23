package ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Book;
import com.example.boket.model.Subscription;
import com.example.boket.ui.addAd.AddAdActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddAdActivityTest {

    ViewInteraction conditionInput;
    ViewInteraction priceInput;
    ViewInteraction publishButton;

    ViewInteraction cityInput;
    ViewInteraction authorTextView;
    ViewInteraction bookTitleTextView;
    ViewInteraction releaseYearTextView;
    ViewInteraction editionTextView;
    ViewInteraction isbnTextView;
    ViewInteraction bookImage;

    private String isbn = "9789144090504";
    @Rule
    public ActivityTestRule<AddAdActivity> activityRule =
            new ActivityTestRule<AddAdActivity>(AddAdActivity.class) {

                @Override
                protected Intent getActivityIntent() {

                    Bundle bundle = new Bundle();
                    bundle.putString("isbn", isbn);
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
        cityInput = onView(withId(R.id.cityInput));
        authorTextView = onView(withId(R.id.authorText));
        bookTitleTextView = onView(withId(R.id.bookTitleText));
        releaseYearTextView = onView(withId(R.id.releaseYear));
        editionTextView = onView(withId(R.id.edition));
        isbnTextView = onView(withId(R.id.isbn));
        bookImage = onView(withId(R.id.bookImage));
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
        cityInput.perform(replaceText("Göteborg"));
        publishButton.perform(scrollTo()).perform(click());

        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void testInputHintsWhenButtonPushed() {
        publishButton.perform(scrollTo()).perform(click());
        conditionInput.check(matches(withHint("Skriv beskrivning av skicket")));
        priceInput.check(matches(withHint("Skriv pris")));
        cityInput.check(matches(withHint("Skriv stad")));
    }

    @Test
    public void testCorrectBookDisplayed() throws InterruptedException {
        BookMockListener bookMockListener = new BookMockListener();
        new Book(isbn, bookMockListener);

        synchronized (bookMockListener) {
            bookMockListener.wait(5000);
        }
        Book book = bookMockListener.getBook();
        assertNotNull("Timed out", book);

        isbnTextView.check(matches(hasValueEqualTo("ISBN: " + book.getIsbn())));
        editionTextView.check(matches(hasValueEqualTo("Upplaga: " + book.getEdition())));
        bookTitleTextView.check(matches(hasValueEqualTo(book.getName())));
        authorTextView.check(matches(hasValueEqualTo(book.getAuthor())));
        releaseYearTextView.check(matches(hasValueEqualTo(book.getReleaseYear())));
    }

    Matcher<View> hasValueEqualTo(final String content) {

        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("Has EditText/TextView the value:  " + content);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextView) && !(view instanceof EditText)) {
                    return false;
                }
                if (view != null) {
                    String text;
                    if (view instanceof TextView) {
                        text = ((TextView) view).getText().toString();
                    } else {
                        text = ((EditText) view).getText().toString();
                    }

                    return (text.equalsIgnoreCase(content));
                }
                return false;
            }
        };
    }

    class BookMockListener implements Book.OnLoadCallback {
        Book book;
        @Override
        public void onLoadComplete(Book book) {
            this.book = book;
            synchronized (this) {
                notifyAll();
            }
        }

        public Book getBook() {
            return this.book;
        }
    }
}

package com.example.boket.ui.addAd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Ad;
import com.example.boket.model.Book;
import com.google.firebase.auth.FirebaseAuth;

/**
 * @author Alexander Jyborn, Oscar Bennet
 *
 * Activity class for presenting and getting data from the AddAd page
 *
 * @since 2020-09-08
 */
public class AddAdActivity extends AppCompatActivity {
    // TODO: 2020-09-09
    //Lagra info i db

    private String isbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);
        //setContentView(R.layout.activity_search_bookseller);

        final EditText conditionEditText = findViewById(R.id.conditionInput);
        final EditText priceEditText = findViewById(R.id.priceInput);
        final EditText cityEditText = findViewById(R.id.cityInput);
        final Button publishButton = findViewById(R.id.publishButton);
        final TextView authorTextView = findViewById(R.id.authorText);
        final TextView titleText = findViewById(R.id.titleText);
        final TextView bookTitleTextView = findViewById(R.id.bookTitleText);
        final TextView releaseYearTextView = findViewById(R.id.releaseYear);
        final TextView editionTextView = findViewById(R.id.edition);
        final TextView isbnTextView = findViewById(R.id.isbn);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            isbn = bundle.getString("BookNumber");

            Book book = new Book(isbn, new Book.OnLoadCallback() {


                @Override
                public void onLoadComplete(Book book) {
                    bookTitleTextView.setText(book.getName());
                    authorTextView.setText(book.getAuthor());
                    releaseYearTextView.setText(book.getReleaseYear());
                    editionTextView.setText("Upplaga: " + book.getEdition());
                    isbnTextView.setText("ISBN: " +book.getIsbn());
                }
            });

        }



    }

    /**
     * Publish an ad and saves it to the database.
     * @param v the associated view
     */
    public void publishAd(View v){
        String condition = ((EditText) findViewById(R.id.conditionInput)).getText().toString();
        double price = Integer.parseInt(((EditText) findViewById(R.id.priceInput)).getText().toString());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Ad ad = new Ad(1, mAuth.getUid(), isbn, price, condition, false);
        ad.save();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        //TODO: Have to check so ad actually uploaded before giving success message.
        Toast.makeText(this, "Ad Succesfully uploaded!", Toast.LENGTH_LONG).show();
    }

}
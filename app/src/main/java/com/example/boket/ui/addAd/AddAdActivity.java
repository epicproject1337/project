package com.example.boket.ui.addAd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Ad;
import com.example.boket.model.Book;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.boket.cameraUtil.common.BarcodeScanner.isValidISBN13;

/**
 * @author Alexander Jyborn, Oscar Bennet
 * <p>
 * Activity class for presenting and creating an Ad from the data on the AddAd page
 * @since 2020-09-08
 */
public class AddAdActivity extends AppCompatActivity {

    private String isbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);
        //setContentView(R.layout.activity_search_add_ad);

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
        final ImageView bookImage = findViewById(R.id.bookImage);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isbn = bundle.getString("BookNumber");
            Log.e("isbn", isbn);
            Book book = new Book(isbn, new Book.OnLoadCallback() {


                @Override
                public void onLoadComplete(Book book) {
                    bookTitleTextView.setText(book.getName());
                    authorTextView.setText(book.getAuthor());
                    releaseYearTextView.setText(book.getReleaseYear());
                    editionTextView.setText("Upplaga: " + book.getEdition());
                    isbnTextView.setText("ISBN: " + book.getIsbn());
                    Log.e("bookImage", book.getImage());
                    Glide.with(AddAdActivity.this).load(book.getImage()).into(bookImage);
                }
            });

        }

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkInputs(priceEditText, conditionEditText)) return;

                double price = Double.parseDouble(String.valueOf(priceEditText.getText()));
                String condition = String.valueOf(conditionEditText.getText());
                String city = String.valueOf(cityEditText.getText());
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                Ad ad = new Ad(userID, email, isbn, price, condition, city, false);
                ad.save();

                Intent backToSearch = new Intent(AddAdActivity.this, MainActivity.class);
                startActivity(backToSearch);
                finish();
                Toast.makeText(getApplicationContext(), "Ad Succesfully uploaded!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean checkInputs(EditText priceEditText, EditText conditionEditText) {
        //TODO fixa styling till validering samt bättre validering
        boolean inputCorrect = true;
        if (priceEditText.getText().length() <= 0) {
            priceEditText.setHint("Skriv pris");
            inputCorrect = false;
        }
        if (conditionEditText.getText().length() <= 0) {
            conditionEditText.setHint("Skriv beskrivning av skicket");
            inputCorrect = false;
        }

        return inputCorrect;
    }

}
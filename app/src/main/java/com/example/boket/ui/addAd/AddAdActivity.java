package com.example.boket.ui.addAd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Ad;
import com.example.boket.model.Book;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.boket.cameraUtil.common.BarcodeScanner.isValidISBN13;

/**
 * @author Alexander Jyborn, Oscar Bennet
 *
 * Activity class for presenting and creating an Ad from the data on the AddAd page
 *
 * @since 2020-09-08
 */
public class AddAdActivity extends AppCompatActivity {

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

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkInputs(priceEditText, conditionEditText)) return;

                double price = Double.parseDouble(String.valueOf(priceEditText.getText()));
                String isbn = String.valueOf(isbnTextView.getText());
                String condition = String.valueOf(conditionEditText.getText());
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                int adID = 0; //TODO hur får man fram id?
                Ad ad = new Ad(adID, userID, isbn, price, condition, false);
                //ad.save();

                Intent backToSearch = new Intent(AddAdActivity.this, MainActivity.class);
                startActivity(backToSearch);
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
        if (conditionEditText.getText().length() <= 0){
            conditionEditText.setHint("Skriv beskrivning av skicket");
            inputCorrect = false;
        }

        return inputCorrect;
    }

}
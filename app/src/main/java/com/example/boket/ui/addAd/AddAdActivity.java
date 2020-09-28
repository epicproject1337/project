package com.example.boket.ui.addAd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.boket.R;
import com.example.boket.model.Book;

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

}
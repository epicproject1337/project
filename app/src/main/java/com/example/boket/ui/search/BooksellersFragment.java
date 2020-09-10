package com.example.boket.ui.search;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;
import com.example.boket.ui.login.LoginActivity;

public class BooksellersFragment extends AppCompatActivity {

    private static final String TAG =  BooksellersFragment.class.getName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ImageView bookImageView = findViewById(R.id.bookImage);
        final TextView bookNameTextView = findViewById(R.id.bookName);
        final TextView bookAuthorTextView = findViewById(R.id.bookAuthor);
        final Button subscribeButton = findViewById(R.id.subscribeButton);
        final RecyclerView adListRecyclerView = findViewById(R.id.adList);

        subscribeButton.setEnabled(true);

        //Set book, image, author
        //Set all booksellers i adList

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If user is not subscribed then, subscribe and change text to "avprenumerera"
                //If user is subscribed then, unsubscribe and change text to "prenumerera"
            }
        });
    }


}

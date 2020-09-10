package com.example.boket.ui.search;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;

public class ABooksellerFragment extends AppCompatActivity {

    private static final String TAG =  ABooksellerFragment.class.getName();

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final ConstraintLayout book = findViewById(R.id.book);
        final TextView bookStateTextView = findViewById(R.id.bookState);
        final TextView bookPriceAndCityTextView = findViewById(R.id.priceAndCity);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open the book with info about the seller
            }
        });
    }

}

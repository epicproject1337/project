package com.example.boket.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;
import com.example.boket.ui.login.LoginActivity;

public class BooksellersFragment extends Fragment{

    private static final String TAG =  BooksellersFragment.class.getName();
    private String ISBN_number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle!=null){
            ISBN_number=bundle.getString("ISBN-nummer");

        }
        //Fill the following parameters with info from database with ISBN number
        View v = inflater.inflate(R.layout.fragment_booksellers,container,false);
        ImageView bookImageView = v.findViewById(R.id.bookImage);
        TextView bookNameTextView = v.findViewById(R.id.bookName);
        TextView bookAuthorTextView = v.findViewById(R.id.bookAuthor);
        Button subscribeButton = v.findViewById(R.id.subscribeButton);
        RecyclerView adListRecyclerView = v.findViewById(R.id.adList);

        subscribeButton.setEnabled(true);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If user is not subscribed then, subscribe and change text to "avprenumerera"
                //If user is subscribed then, unsubscribe and change text to "prenumerera"
            }
        });

        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

    }



}

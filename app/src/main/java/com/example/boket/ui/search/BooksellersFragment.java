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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;
import com.example.boket.model.Book;
import com.example.boket.ui.login.LoginActivity;

import java.text.BreakIterator;
import java.util.ArrayList;

public class BooksellersFragment extends Fragment{


    private static final String TAG = BooksellersFragment.class.getName();
    private String ISBN_number;
    private ImageView bookImageView;
    private TextView bookNameTextView;
    private TextView bookAuthorTextView;
    //private Button subscribeButton;

    private BookAdapter bookAdapter;
    private ArrayList<String> items;
    private RecyclerView adListRecyclerView;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("tariks funkar");

        Bundle bundle = getArguments();
        if (bundle != null) {
            ISBN_number = bundle.getString("ISBN-nummer");

        }


        //Fill the following parameters with info from database with ISBN number
        View v = inflater.inflate(R.layout.fragment_booksellers, container, false);
        bookImageView = v.findViewById(R.id.bookImage);
        bookNameTextView = v.findViewById(R.id.bookName);
        bookAuthorTextView = v.findViewById(R.id.bookAuthor);
        final Button subscribeButton = (Button) v.findViewById(R.id.subscribeButton);

        items = new ArrayList<>();
        items.add("hej");
        items.add("detta");
        items.add("funkar");
        items.add("inte");


        bookAdapter = new BookAdapter(this.getContext(), items);
        adListRecyclerView = v.findViewById(R.id.adList);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        adListRecyclerView.setLayoutManager(llm);
        adListRecyclerView.setAdapter(bookAdapter);


        //subscribeButton.setEnabled(true);
        //subscribeButton.setOnClickListener(this);

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked");
                subscribeButton.setText("heeej");
                Toast.makeText(getActivity(), "haalooooo", Toast.LENGTH_LONG);
                //subscribeButtonClicked();
                //If user is not subscribed then, subscribe and change text to "avprenumerera"
                //If user is subscribed then, unsubscribe and change text to "prenumerera"
            }
        });




        return inflater.inflate(R.layout.fragment_booksellers, container, false);
    }

    /*
    public void onClick(View v) {
        System.out.println("inne");
        switch (v.getId()) {
            case R.id.subscribeButton:
                System.out.println("inne2");
                subscribeButton.setText("hej");
                System.out.println("inne3");
                break;
            default:
                break;
        }
    }


    public void subscribeButtonClicked() {

        subscribeButton.setText("hej");
    }
     */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
/*
    @Override
    public void onClick(View view) {
        System.out.println("clicked");
        switch (view.getId()) {
            case R.id.subscribeButton:
                System.out.println("kom in hit");
                subscribeButton.setText("heeej");
                break;
        }
    }

 */
}




package com.example.boket.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;
import com.example.boket.model.Book;

import java.util.ArrayList;

public class BooksellersFragment extends Fragment {


    private static final String TAG = BooksellersFragment.class.getName();
    private String ISBN_number;
    private ImageView bookImageView;
    private TextView bookNameTextView;
    private TextView bookAuthorTextView;
    private TextView releaseYearTextView;
    private TextView editionTextView;
    private TextView isbnTextView;
    private Button subscribeButton;
    private BookAdapter bookAdapter2;
    private ArrayList<String> items;
    private RecyclerView adListRecyclerView;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_booksellers, container, false);
        init(v);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ISBN_number = bundle.getString("BookNumber");

            Book book = new Book(ISBN_number, new Book.OnLoadCallback() {
                @Override
                public void onLoadComplete(Book book) {
                    bookNameTextView.setText(book.getName());
                    bookAuthorTextView.setText(book.getAuthor());
                    releaseYearTextView.setText(book.getReleaseYear());
                    editionTextView.setText("Upplaga: " + book.getEdition());
                    isbnTextView.setText("ISBN: " +book.getIsbn());
                }
            });
        }






        return v;
    }

    private void init(View v) {
        bookImageView = v.findViewById(R.id.bookImage);
        bookNameTextView = v.findViewById(R.id.bookName);
        bookAuthorTextView = v.findViewById(R.id.bookAuthor);
        subscribeButton = v.findViewById(R.id.subscribeButton);
        releaseYearTextView = v.findViewById(R.id.releaseYear);
        editionTextView = v.findViewById(R.id.edition);
        isbnTextView = v.findViewById(R.id.isbn);


        adListRecyclerView = v.findViewById(R.id.adList);
        adListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bookAdapter2 = new BookAdapter(getContext(), getBookSellersList(v));
        adListRecyclerView.setAdapter(bookAdapter2);

        bookNameTextView.setText(ISBN_number);

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeButtonClicked();
            }
        });
    }

    private ArrayList<ABookSeller> getBookSellersList(View v){
        ArrayList<ABookSeller> bookSellersList = new ArrayList<>();


        //String state, String price, String city, View
        ABookSeller aBookSeller2 = new ABookSeller("orginal","kompispres","skövde",v);
        bookSellersList.add(aBookSeller2);
        bookSellersList.add(aBookSeller2);
        bookSellersList.add(aBookSeller2);
        bookSellersList.add(aBookSeller2);

        return  bookSellersList;
    }

    private void subscribeButtonClicked(){
        CharSequence chSeq = subscribeButton.getText();
        String btntxt = chSeq.toString();
        if(btntxt.equals("Prenumerera")){
            subscribeButton.setText("Avprenumerera");
        }else{
            subscribeButton.setText("Prenumerera");
        }
    }

    public void onIABookSellerCL(View v, int position) {


    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}




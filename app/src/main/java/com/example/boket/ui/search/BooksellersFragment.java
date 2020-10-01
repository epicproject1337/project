package com.example.boket.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.boket.R;
import com.example.boket.model.Ad;
import com.example.boket.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @author Tarik Porobic
 * @since ${2020-10-1}
 */
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
    private BookAdapter bookAdapter;
    private ArrayList<String> items;
    private RecyclerView adListRecyclerView;
    private TextView sorryText;
    private TextView pressSubText;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_booksellers, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ISBN_number = bundle.getString("BookNumber");

        }
        init(v);

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
        sorryText = v.findViewById(R.id.sorryText);
        pressSubText = v.findViewById(R.id.pressSubText);

        setBookInfo(v);

        adListRecyclerView = v.findViewById(R.id.adList);
        adListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setBookSellersList(v);

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeButtonClicked();
            }
        });
    }

    private void setBookInfo(View v){
        Book book = new Book(ISBN_number, new Book.OnLoadCallback() {
            @Override
            public void onLoadComplete(Book book) {
                bookNameTextView.setText(book.getName());
                bookAuthorTextView.setText(book.getAuthor());
                releaseYearTextView.setText(book.getReleaseYear());
                editionTextView.setText("Upplaga: " + book.getEdition());
                isbnTextView.setText("ISBN: " +book.getIsbn());
                //Picasso.get().load(book.getImage()).into(bookImageView);
                Glide.with(v).load(book.getImage()).into(bookImageView);
            }
        });
    }

    private void setBookSellersList(View v){
        ArrayList<ABookSeller> bookSellersList = new ArrayList<>();

        Ad.getAdsByISBN(ISBN_number, new Ad.GetAdsCallback() {
            @Override
            public void onGetAdsComplete(ArrayList<Ad> adList) {
               for (int i = 0 ; i<adList.size();i++){
                   Ad ad = adList.get(i);
                   String state = ad.getCondition();
                   Double price = ad.getPrice();
                   //TODO fixa city i ad
                   //String city =
                   ABookSeller aBookSeller = new ABookSeller(state,price.toString(),"Göteborg",v);
                   bookSellersList.add(aBookSeller);
               }
               if (adList.size()==0){
                   sorryText.setVisibility(View.VISIBLE);
                   pressSubText.setVisibility(View.VISIBLE);
               }
                bookAdapter = new BookAdapter(getContext(), bookSellersList);
                adListRecyclerView.setAdapter(bookAdapter);
            }
        });
/*
        //String state, String price, String city, View
        ABookSeller aBookSeller2 = new ABookSeller("orginal","kompispres","skövde",v);
        bookSellersList.add(aBookSeller2);
        bookSellersList.add(aBookSeller2);
        bookSellersList.add(aBookSeller2);
        bookSellersList.add(aBookSeller2);

 */
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




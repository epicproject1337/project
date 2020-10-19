package com.example.boket.ui.bookSeller;

import android.content.Context;
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
import com.example.boket.model.Subscription;
import com.example.boket.model.user.LocalUser;


import java.util.ArrayList;

/**
 * @author Tarik Porobic
 * <p>
 * Class that is connected to fragment_booksellers
 * @since 2020-09-10
 */
public class BooksellersFragment extends Fragment {

    private boolean isSubscribedToBook;
    private Button subscribeButton;
    private LocalUser user = LocalUser.getCurrentUser();
    private String ISBN_number;

    /**
     * Gets the ISBN number of the book from the previous screen (search) and initiate all buttons,
     * texts and so
     *
     * @param inflater           android parameter
     * @param container          android parameter
     * @param savedInstanceState android parameter
     * @return The edited view for booksellersFragment
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_booksellers, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            ISBN_number = bundle.getString("BookNumber");
        }

        try {
            init(v);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return v;
    }

    private void init(View v) throws InterruptedException {
        subscribeButton = v.findViewById(R.id.subscribeButton);
        ImageView bookImageView = v.findViewById(R.id.bookImage);
        TextView bookNameTextView = v.findViewById(R.id.bookName);
        TextView bookAuthorTextView = v.findViewById(R.id.bookAuthor);
        TextView releaseYearTextView = v.findViewById(R.id.releaseYear);
        TextView editionTextView = v.findViewById(R.id.edition);
        TextView isbnTextView = v.findViewById(R.id.isbn);
        TextView sorryText = v.findViewById(R.id.sorryText);
        TextView pressSubText = v.findViewById(R.id.pressSubText);

        setBookInfo(v, bookNameTextView, bookAuthorTextView, releaseYearTextView,
                editionTextView, isbnTextView, bookImageView);

        setRecyclerView(v, bookNameTextView,sorryText,pressSubText);

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeButtonClicked();
            }
        });
    }

    private void setBookInfo(View v, TextView bookNameTextView, TextView bookAuthorTextView,
                             TextView releaseYearTextView, TextView editionTextView,
                             TextView isbnTextView, ImageView bookImageView) {
        Book book = new Book(ISBN_number, new Book.OnLoadCallback() {
            @Override
            public void onLoadComplete(Book book) {
                bookNameTextView.setText(book.getName());
                bookAuthorTextView.setText(book.getAuthor());
                releaseYearTextView.setText(book.getReleaseYear());
                editionTextView.setText("Upplaga: " + book.getEdition());
                isbnTextView.setText("ISBN: " + book.getIsbn());

                if (v.isShown()) {
                    Glide.with(v).load(book.getImage()).into(bookImageView);
                }
                Subscription.isSubscribed(book.getIsbn(), user.getUid(), new Subscription.OnLoadCallback() {
                    @Override
                    public void isSubscribedCallback(boolean isSubscribed) {
                        System.out.println(isSubscribed);
                        isSubscribedToBook = isSubscribed;
                        if (isSubscribed) {
                            subscribeButton.setText("Avprenumerera");
                        } else {
                            subscribeButton.setText("Prenumerera");
                        }
                    }
                });
            }
        });
    }

    private void setRecyclerView(View v, TextView bookNameTextView, TextView sorryText,
                                 TextView pressSubText) throws InterruptedException {

        RecyclerView adListRecyclerView = v.findViewById(R.id.adList);
        adListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<ABookSeller> bookSellersList = new ArrayList<>();
        Context c = getContext();

        final BookAdapter[] bookAdapter = new BookAdapter[1];
        Thread.sleep(100);
        Ad.getAdsByISBN(ISBN_number, new Ad.GetAdsCallback() {
            @Override
            public void onGetAdsComplete(ArrayList<Ad> adList) {

                if (adList.size() == 0) {
                    sorryText.setVisibility(View.VISIBLE);
                    pressSubText.setVisibility(View.VISIBLE);
                } else {
                    for (Ad ad : adList) {
                        String state = ad.getCondition();
                        String price = Double.toString(ad.getPrice());
                        String sellerEmail = ad.getEmail();
                        String bookSold = String.valueOf(bookNameTextView.getText());
                        String city = ad.getCity();

                        ABookSeller aBookSeller = new ABookSeller(bookSold, sellerEmail, state, price, city);
                        bookSellersList.add(aBookSeller);
                    }
                }

                sortCheapestFirst(bookSellersList);
                bookAdapter[0] = new BookAdapter(c, bookSellersList);
                adListRecyclerView.setAdapter(bookAdapter[0]);
            }
        });
    }


    private void sortCheapestFirst(ArrayList<ABookSeller> bookSellersList) {
        for (int i = 0; i < bookSellersList.size() - 1; i++) {
            for (int j = 0; j < bookSellersList.size() - 1 - i; j++) {
                ABookSeller abs = bookSellersList.get(j);
                ABookSeller abs2 = bookSellersList.get(j + 1);
                if (Double.parseDouble(abs.getPrice()) > Double.parseDouble(abs2.getPrice())) {
                    ABookSeller tmp = abs;
                    bookSellersList.set(j, abs2);
                    bookSellersList.set(j + 1, tmp);
                }
            }
        }
    }


    private void subscribeButtonClicked() {
        if (isSubscribedToBook) {
            Subscription.unsubscribeUser(ISBN_number, user.getUid());
            subscribeButton.setText("Prenumerera");
        } else {
            Subscription.subscribeUser(ISBN_number, user.getUid());
            subscribeButton.setText("Avprenumerera");
        }
        isSubscribedToBook = !isSubscribedToBook;
    }

    /**
     * Android method
     *
     * @param savedInstanceState android parameter
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}




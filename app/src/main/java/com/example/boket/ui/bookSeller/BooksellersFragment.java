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
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * @author Tarik Porobic
 * <p>
 * Class that is connected to fragment_booksellers
 * @since 2020-09-10
 */
public class BooksellersFragment extends Fragment {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
    private RecyclerView adListRecyclerView;
    private boolean isSubscribedToBook;
    private TextView sorryText;
    private TextView pressSubText;

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

    private void setBookInfo(View v) {
        Book book = new Book(ISBN_number, new Book.OnLoadCallback() {
            @Override
            public void onLoadComplete(Book book) {
                bookNameTextView.setText(book.getName());
                //System.out.println("book.getName(): "+book.getName());
                //System.out.println("bookNameTextView: "+ String.valueOf(bookNameTextView.getText()));
                bookAuthorTextView.setText(book.getAuthor());
                releaseYearTextView.setText(book.getReleaseYear());
                editionTextView.setText("Upplaga: " + book.getEdition());
                isbnTextView.setText("ISBN: " + book.getIsbn());
                Glide.with(v).load(book.getImage()).into(bookImageView);
                Subscription.isSubscribed(book.getIsbn(), mAuth.getUid(), new Subscription.OnLoadCallback() {
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

    private void setBookSellersList(View v) {
        ArrayList<ABookSeller> bookSellersList = new ArrayList<>();
        Context c = getContext();

        Ad.getAdsByISBN(ISBN_number, new Ad.GetAdsCallback() {
            @Override
            public void onGetAdsComplete(ArrayList<Ad> adList) {
                for (Ad ad: adList ) {
                    String state = ad.getCondition();
                    String price = Double.toString(ad.getPrice());
                    String sellerEmail = ad.getEmail();
                    String bookSold = String.valueOf(bookNameTextView.getText());
                    //System.out.println("Boken som säljs är " + bookSold);
                    String city = ad.getCity();

                    ABookSeller aBookSeller = new ABookSeller(bookSold, sellerEmail, state, price, city, v);
                    bookSellersList.add(aBookSeller);
                }
                if (adList.size() == 0) {
                    sorryText.setVisibility(View.VISIBLE);
                    pressSubText.setVisibility(View.VISIBLE);
                }
                sortCheapestFirst(bookSellersList);
                bookAdapter = new BookAdapter(c, bookSellersList);
                adListRecyclerView.setAdapter(bookAdapter);


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
            Subscription.unsubscribeUser(ISBN_number, mAuth.getUid());
            subscribeButton.setText("Prenumerera");
        } else {
            Subscription.subscribeUser(ISBN_number, mAuth.getUid());
            subscribeButton.setText("Avprenumerera");
        }
        isSubscribedToBook = !isSubscribedToBook;
    }

    public void onIABookSellerCL(View v, int position) {


    }

    /**
     * Android method
     *
     * @param savedInstanceState android parameter
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public RecyclerView getRecyclerView() {
        return adListRecyclerView;
    }

}




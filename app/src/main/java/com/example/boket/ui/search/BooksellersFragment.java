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

public class BooksellersFragment extends Fragment {


    private static final String TAG = BooksellersFragment.class.getName();
    private String ISBN_number;
    private ImageView bookImageView;
    private TextView bookNameTextView;
    private TextView bookAuthorTextView;
    private Button subscribeButton;

    private BookAdapter2 bookAdapter2;
    private ArrayList<String> items;
    private RecyclerView adListRecyclerView;

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

        adListRecyclerView = v.findViewById(R.id.adList);
        adListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        bookAdapter2 = new BookAdapter2(getContext(), getBookSellersList());
        adListRecyclerView.setAdapter(bookAdapter2);

        bookNameTextView.setText(ISBN_number);

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeButtonClicked();
            }
        });
    }

    private ArrayList<ABookSeller> getBookSellersList(){
        ArrayList<ABookSeller> bookSellersList = new ArrayList<>();

        ABookSeller aBookSeller = new ABookSeller();
        aBookSeller.setCity("Göteborg");
        aBookSeller.setPrice("Gratis");
        aBookSeller.setState("Skit braaa");

        ABookSeller aBookSeller2 = new ABookSeller();
        aBookSeller2.setCity("Skövde");
        aBookSeller2.setPrice("kompis pris");
        aBookSeller2.setState("original");

        ABookSeller aBookSeller3 = new ABookSeller();
        aBookSeller3.setCity("Bagdad");
        aBookSeller3.setPrice("99999");
        aBookSeller3.setState("bästaste");

        bookSellersList.add(aBookSeller);
        bookSellersList.add(aBookSeller2);
        bookSellersList.add(aBookSeller3);
        bookSellersList.add(aBookSeller3);
        bookSellersList.add(aBookSeller3);
        bookSellersList.add(aBookSeller3);
        bookSellersList.add(aBookSeller3);
        bookSellersList.add(aBookSeller3);
        bookSellersList.add(aBookSeller);

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


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}




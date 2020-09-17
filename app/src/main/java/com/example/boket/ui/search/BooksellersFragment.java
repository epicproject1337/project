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

    private BookAdapter bookAdapter;
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


        items = new ArrayList<>();
        items.add("hej");
        items.add("detta");
        items.add("funkar");
        items.add("inte");

        System.out.println("här");
        //bookAdapter = new BookAdapter(this.getContext(), items);
        adListRecyclerView = v.findViewById(R.id.adList);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        adListRecyclerView.setLayoutManager(llm);
        adListRecyclerView.setAdapter(bookAdapter);


        return v;
    }

    private void init(View v) {
        bookImageView = v.findViewById(R.id.bookImage);
        bookNameTextView = v.findViewById(R.id.bookName);
        bookAuthorTextView = v.findViewById(R.id.bookAuthor);
        subscribeButton = v.findViewById(R.id.subscribeButton);

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscribeButtonClicked();
            }
        });
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




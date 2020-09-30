package com.example.boket.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Ad;
import com.example.boket.model.Book;
import com.example.boket.model.Subscription;
import com.example.boket.ui.RecyclerViewClickListener;
import com.example.boket.ui.search.BookItem;
import com.example.boket.ui.search.BookItemAdapter;
import com.example.boket.ui.search.BooksellersFragment;
import com.example.boket.ui.search.SearchFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements RecyclerViewClickListener {

    private RecyclerView buyAds;
    private RecyclerView sellAds;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ItemAdapter itemAdapter;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment

        // Add the following lines to create RecyclerView
        buyAds = view.findViewById(R.id.buyAds);
        buyAds.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        buyAds.setLayoutManager(layoutManager);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        itemAdapter = new ItemAdapter(view.getContext(), this, new ArrayList<Book>());
        buyAds.setAdapter(itemAdapter);
        RecyclerViewClickListener that = this;
        Subscription.getSubscribedBooks(mAuth.getUid(), new Subscription.OnLoadSubscribedBooksCallback() {
            @Override
            public void onCompleteCallback(ArrayList<Book> books) {
                itemAdapter = new ItemAdapter(view.getContext(), that, books);
                buyAds.setAdapter(itemAdapter);
            }
        });

        /*
        sellAds = view.findViewById(R.id.sellAds);
        sellAds.setHasFixedSize(true);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        sellAds.setLayoutManager(layoutManager2);

        Ad.getAdsByUser(mAuth.getUid(), new Ad.GetAdsCallback() {
            @Override
            public void onGetAdsComplete(ArrayList<Ad> adList) {
                sellAds.setAdapter(new ItemAdapter(view.getContext(), that, adList));
            }
        });*/

        ImageButton signOutButton = (ImageButton) view.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).signOut();
            }
        });

        return view;
    }


    @Override
    public void recyclerViewListClicked(View v, int position) {
        Book book = itemAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("BookNumber", book.getIsbn());
        BooksellersFragment booksellersFragment = new BooksellersFragment();
        booksellersFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, booksellersFragment)
                .addToBackStack("ProfileFragment")
                .commit();
    }
}
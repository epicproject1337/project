package com.example.boket.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Ad;
import com.example.boket.model.Book;
import com.example.boket.model.Subscription;
import com.example.boket.model.user.LocalUser;
import com.example.boket.ui.RecyclerViewClickListener;
import com.example.boket.ui.bookSeller.BooksellersFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * @author Albin Landgren
 * @since 2020-09-08
 */
public class ProfileFragment extends Fragment implements RecyclerViewClickListener {

    private RecyclerView subscribedBooksView;
    private RecyclerView adsView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SubscribedBookAdapter subscribedBookAdapter;
    private ManageAdAdapter manageAdAdapter;
    private TextView profileName;

    /**
     * Required empty constructor
     */
    public ProfileFragment() {
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileName = view.findViewById(R.id.profileName);
        profileName.setText(setRightText());

        // Creating RecyclerView for subscribed books
        subscribedBooksView = view.findViewById(R.id.subscribedBooksView);
        subscribedBooksView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        subscribedBooksView.setLayoutManager(layoutManager);

        subscribedBookAdapter = new SubscribedBookAdapter(view.getContext(), this, new ArrayList<Book>());
        subscribedBooksView.setAdapter(subscribedBookAdapter);
        ProfileFragment that = this;
        Subscription.getSubscribedBooks(LocalUser.getCurrentUser().getUid(), new Subscription.OnLoadSubscribedBooksCallback() {
            @Override
            public void onCompleteCallback(ArrayList<Book> books) {
                subscribedBookAdapter = new SubscribedBookAdapter(view.getContext(), that, books);
                subscribedBooksView.setAdapter(subscribedBookAdapter);
            }
        });


        // Creating RecyclerView for own ads
        adsView = view.findViewById(R.id.adsView);
        adsView.setHasFixedSize(true);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        adsView.setLayoutManager(layoutManager2);
        manageAdAdapter = new ManageAdAdapter(view.getContext(), this, new ArrayList<Ad>());
        Ad.getAdsByUser(LocalUser.getCurrentUser().getUid(), false, new Ad.GetAdsCallback() {
            @Override
            public void onGetAdsComplete(ArrayList<Ad> adList) {
                manageAdAdapter = new ManageAdAdapter(view.getContext(), that, adList);
                adsView.setAdapter(manageAdAdapter);
            }
        });

        // Set onclick behavior for sign out button
        ImageButton signOutButton = (ImageButton) view.findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).signOut();
            }
        });

        return view;
    }

    /**
     * @return concatenated string containing the name of the user
     */
    private String setRightText() {
        String returnString = LocalUser.getCurrentUser().getName();
        return returnString.split(" ", 2)[0].concat("'s profil");

        /*for (int i = 0; i < returnString.length(); i++) {
            if (returnString.charAt(i)== ' ') {
                returnString = returnString.substring(0, i);
                break;
            }
        }
        return returnString+"'s profil";*/
    }

    /**
     * Handle click on RecyclerView item
     *
     * @param v
     * @param position the position of the clicked item
     */
    @Override
    public void recyclerViewListClicked(View v, int position) {
        Book book = subscribedBookAdapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putString("isbn", book.getIsbn());
        BooksellersFragment booksellersFragment = new BooksellersFragment();
        booksellersFragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, booksellersFragment)
                .addToBackStack(null)
                .commit();
    }
}
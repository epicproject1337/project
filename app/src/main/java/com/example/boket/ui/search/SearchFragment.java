package com.example.boket.ui.search;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.boket.R;
import com.example.boket.model.Book;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    private TextInputLayout searchBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        //onClickListener
        BooksellersFragment booksellersFragment = new BooksellersFragment();
        Bundle bundle = new Bundle();
        bundle.putString("BookNumber", "ISBN-nummer"); //Skriv valda bokens ISBN-nummer
        booksellersFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, booksellersFragment).commit();
        //getFragmentManager().beginTransaction().replace(BooksellersFragment,this)

        View v = inflater.inflate(R.layout.fragment_search, container, false);
        searchBar = v.findViewById(R.id.searchBar);


        return inflater.inflate(R.layout.fragment_search, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        // TODO: Use the ViewModel
    }

    public String getInput() {
        return searchBar.getEditText().toString();
    }

    private ArrayList<Book> fetchBooks() {
        String input = getInput();
        ArrayList books = searchViewModel.fetchBooks(input);

        return books;
    }

}
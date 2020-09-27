package com.example.boket.ui.search;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.boket.R;
import com.example.boket.model.Book;
import com.example.boket.model.ISearch;
import com.example.boket.model.Search;
import com.example.boket.ui.RecyclerViewClickListener;
import com.example.boket.ui.addAd.AddAdActivity;
import com.example.boket.ui.addAd.SearchBookseller;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements RecyclerViewClickListener, SearchView.OnQueryTextListener{

    private SearchViewModel searchViewModel;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    private SearchView searchView;
    private RecyclerView recyclerView;
    private BookItemAdapter bookItemAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = v.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));



        return v;
    }




    private ArrayList<BookItem> getBookItems(){
        ArrayList<BookItem> bookItems = new ArrayList<>();
        ArrayList<Book> books = searchViewModel.getBooks();

        for(Book book : books){
            String isbn = book.getIsbn();
            String title = book.getName();
            String author = book.getAuthor();
            String edition = book.getEdition();
            String releaseYear = book.getReleaseYear();
            String image = book.getImage();

            BookItem bi = new BookItem(this.getContext(), isbn, title, author, edition, releaseYear, image);
            bookItems.add(bi);
        }

        return bookItems;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        // TODO: Use the ViewModel
    }

    private void searchBooks(String query) {
    RecyclerViewClickListener i = this;
        Search.searchBooks(query, new Search.SearchCallback() {

            @Override
            public void onSearchBooks(ArrayList<Book> bookList) {
                searchViewModel.setBooks(bookList);
                bookItemAdapter = new BookItemAdapter(getContext(), i, getBookItems());
                recyclerView.setAdapter(bookItemAdapter);
            }
        });

    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        BookItem book = bookItemAdapter.getItem(position);
        System.out.println(book.getBookTitle());

        sendISBN(book);


    }

    private void sendISBN(BookItem book){
        Bundle bundle = new Bundle();
        bundle.putString("BookNumber", book.getIsbn());

        Activity activity = getActivity();
        if(activity instanceof SearchBookseller){
            Intent intent = new Intent(activity, AddAdActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else {
            BooksellersFragment booksellersFragment = new BooksellersFragment();
            booksellersFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, booksellersFragment).commit();
            // BooksellersFragment booksellersFragment = new BooksellersFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, booksellersFragment);
            transaction.commit();
        }
    }



    @Override
    public boolean onQueryTextSubmit(String s) {
        System.out.println(searchView.getQuery());
        searchBooks(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
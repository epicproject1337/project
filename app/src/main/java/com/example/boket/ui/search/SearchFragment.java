package com.example.boket.ui.search;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.Book;
import com.example.boket.controller.Search;
import com.example.boket.ui.RecyclerViewClickListener;
import com.example.boket.ui.addAd.AddAdActivity;
import com.example.boket.ui.addAd.SearchAddAdActivity;
import com.example.boket.ui.bookSeller.BooksellersFragment;
import com.example.boket.ui.camera.BarcodeScannerActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Oscar Bennet
 * <p>
 * Fragment for the search page where the user searches for books
 * @since 2020-09-10
 */

public class SearchFragment extends Fragment implements RecyclerViewClickListener, SearchView.OnQueryTextListener {

    private SearchViewModel searchViewModel;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private BookItemAdapter bookItemAdapter;
    private ImageButton cameraButton;
    private TextView noBookTextView;
    private TextView hintText;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    /**
     * Creates the view of the search page
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the view of search fragment
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = v.findViewById(R.id.searchView);
        recyclerView = v.findViewById(R.id.recyclerView);
        noBookTextView = v.findViewById(R.id.noBookTextView);
        hintText = v.findViewById(R.id.hintText);
        cameraButton = v.findViewById(R.id.cameraButton);

        searchView.setOnQueryTextListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        bookItemAdapter = new BookItemAdapter(getContext(), this, new ArrayList<BookItem>());
        recyclerView.setAdapter(bookItemAdapter);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if(getActivity() instanceof MainActivity){
                    bundle.putString("PrevPage", "searchFragment");
                }
                else if(getActivity() instanceof AddAdActivity){
                    bundle.putString("PrevPage", "addAdActivity");
                }


                Intent intent = new Intent(getContext(), BarcodeScannerActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        return v;
    }


    private ArrayList<BookItem> getBookItems() {
        ArrayList<BookItem> bookItems = new ArrayList<>();
        ArrayList<Book> books = searchViewModel.getBooks();

        for (Book book : books) {
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

    /**
     * Get the view model
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
    }

    private void updateRecyclerView(ArrayList<BookItem> bookItems){
        RecyclerViewClickListener i = this;
        bookItemAdapter = new BookItemAdapter(getContext(), i, bookItems);
        recyclerView.setAdapter(bookItemAdapter);
    }

    private void startIsBookFoundTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        ArrayList books = searchViewModel.getBooks();
                        if(books == null || books.size() == 0){
                            noBookTextView.setVisibility(View.VISIBLE);
                        }
                        else{
                            noBookTextView.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        }, 5*1000);
    }

    private void searchBooks(String query) {

        //Clears recyclerview
        searchViewModel.getBooks().clear();
        updateRecyclerView(getBookItems());

        Search.searchBooks(query, new Search.SearchCallback() {

            @Override
            public void onSearchBooks(ArrayList<Book> bookList) {
                noBookTextView.setVisibility(View.INVISIBLE);
                searchViewModel.setBooks(bookList);
                updateRecyclerView(getBookItems());
            }
        });

        startIsBookFoundTimer();

    }

    /**
     * Handle clicked book item
     *
     * @param v
     * @param position the position of the item
     */
    @Override
    public void recyclerViewListClicked(View v, int position) {
        BookItem book = bookItemAdapter.getItem(position);
        sendISBN(book);
    }

    /**
     * Navigates and send the ISBN to either BookSeller or AddAd
     *
     * @param book
     */
    private void sendISBN(BookItem book) {
        Bundle bundle = new Bundle();
        bundle.putString("BookNumber", book.getIsbn());

        Activity activity = getActivity();
        if (activity instanceof SearchAddAdActivity) {
            Intent intent = new Intent(activity, AddAdActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            BooksellersFragment booksellersFragment = new BooksellersFragment();
            booksellersFragment.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, booksellersFragment)
                    .addToBackStack("SearchFragment")
                    .commit();
        }
    }

    /**
     * Handles submitted text from the search bar
     *
     * @param s Input text from the user
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String s) {
        hintText.setVisibility(View.INVISIBLE);
        searchBooks(s);

        return false;
    }

    /**
     * Listener if query text is changed
     *
     * @param s Input text from the user
     * @return
     */
    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

}
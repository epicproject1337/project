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

import com.example.boket.R;
import com.example.boket.model.Book;
import com.example.boket.controller.Search;
import com.example.boket.ui.RecyclerViewClickListener;
import com.example.boket.ui.addAd.AddAdActivity;
import com.example.boket.ui.addAd.SearchAddAd;
import com.example.boket.ui.camera.BarcodeScannerActivity;

import java.util.ArrayList;

/**
 * @author Oscar Bennet
 *
 * Fragment for the search page where the user searches for books
 *
 * @since 2020-09-10
 */

public class SearchFragment extends Fragment implements RecyclerViewClickListener, SearchView.OnQueryTextListener {

    private SearchViewModel searchViewModel;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    private SearchView searchView;
    private RecyclerView recyclerView;
    private BookItemAdapter bookItemAdapter;
    private ImageButton cameraButton;
    private TextView noBookTextView;

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
        searchView.setOnQueryTextListener(this);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        noBookTextView = v.findViewById(R.id.noBookTextView);

        cameraButton = v.findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BarcodeScannerActivity.class);
                startActivity(intent);
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        //Bad solution TODO better solution
        Activity activity = getActivity();
        if (activity instanceof SearchAddAd) {
            ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
            params.height = 1200;
            recyclerView.setLayoutParams(params);
        }



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
     * @param savedInstanceState
     */
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
                //noBookTextView.setVisibility(View.INVISIBLE);
                searchViewModel.setBooks(bookList);
                ArrayList<Book> books = searchViewModel.getBooks();
                if(books.size() > 0) {

                    bookItemAdapter = new BookItemAdapter(getContext(), i, getBookItems());
                    recyclerView.setAdapter(bookItemAdapter);
                }
                else{
                    noBookTextView.setVisibility(View.VISIBLE);
                    System.out.println("NO BOOK");
                }
            }

        });

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
        System.out.println(book.getBookTitle());

        sendISBN(book);


    }


    /**
     * Navigates and send the ISBN to either BookSeller or AddAd
     * @param book
     */
    public void sendISBN(BookItem book) {
        Bundle bundle = new Bundle();
        bundle.putString("BookNumber", book.getIsbn());

        Activity activity = getActivity();
        if (activity instanceof SearchAddAd) {
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
     * @param s Input text from the user
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String s) {
        System.out.println(searchView.getQuery());
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
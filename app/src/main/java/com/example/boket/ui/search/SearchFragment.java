package com.example.boket.ui.search;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

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

import com.example.boket.R;
import com.example.boket.model.Book;
import com.example.boket.model.ISearch;
import com.example.boket.model.Search;
import com.example.boket.ui.RecyclerViewClickListener;
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
    private BookItem bookItem;
    private BookItemAdapter bookItemAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_search, container, false);
        searchView = v.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //bookItemAdapter = new BookItemAdapter(getContext(), this, getBookItems());
        //recyclerView.setAdapter(bookItemAdapter);



        return v;
    }




    private ArrayList<BookItem> getBookItems(){
        ArrayList<BookItem> bookItems = new ArrayList<>();
        ArrayList<Book> books = searchViewModel.getBooks();
        //ArrayList<Book> books = new ArrayList<Book>();
        //Book book1 = new Book("hje","de","fsf","fds","fsf","ffs");
        //books.add(book1);

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

        /*
        BookItem b1 = new BookItem(this.getContext());
        b1.setBookTitle("The life of a KING");
        b1.setAuthor("Oscar Bennet");
        b1.setPublishedYear("2020");

        BookItem b2 = new BookItem(this.getContext());
        b2.setBookTitle("The life of a SIMP");
        b2.setAuthor("Albin Landgren");
        b2.setPublishedYear("2020");


        bookItems.add(b1);
        bookItems.add(b2);

         */
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
        System.out.println("BITCH3");
        Search.searchBooks(query, new Search.SearchCallback() {

            @Override
            public void onSearchBooks(ArrayList<Book> bookList) {
                System.out.println("BITCH");
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

        goToBookSellers(book);


    }

    private void goToBookSellers(BookItem book){
        BooksellersFragment booksellersFragment = new BooksellersFragment();
        Bundle bundle = new Bundle();
        bundle.putString("BookNumber", book.getBookTitle()); //Skriv valda bokens ISBN-nummer i "ISBN-nummer"
        booksellersFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, booksellersFragment).commit();
        // BooksellersFragment booksellersFragment = new BooksellersFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment,booksellersFragment);
        transaction.commit();
    }



    @Override
    public boolean onQueryTextSubmit(String s) {
        System.out.println(searchView.getQuery());
        searchBooks(s);
        //bookItemAdapter = new BookItemAdapter(getContext(), this, getBookItems());
        //recyclerView.setAdapter(bookItemAdapter);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
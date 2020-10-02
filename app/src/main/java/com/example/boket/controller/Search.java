package com.example.boket.controller;

import android.util.Log;

import com.example.boket.model.Book;
import com.example.boket.model.integrations.Algolia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * @author Pajam Khoshnam
 *
 * A controller for handling search queries.
 *
 * @since 2020-09-16
 */
public class Search {

    private static final String bookIndex = "BOOKINDEX";


    public Search() {
    }

    /**
     * method to search for books by entering an isbn, name or author. Returns a list of books
     * that matches the query through a callback method.
     * @param query A search query, can be full or part of name, author or isbn.
     * @param searchCallback callback method that will receive an ArrayList of books that is found
     *                       with the given query.
     */
    public static void searchBooks(String query, SearchCallback searchCallback) {
        Algolia algolia = new Algolia(bookIndex);
        ArrayList<Book> books = new ArrayList<Book>();
        algolia.search(query, new ISearch.SearchCallback() {
            @Override
            public void onSearchComplete(JSONObject content) {
                try {
                    JSONArray hits = content.getJSONArray("hits");
                    Log.d("JSON", "SUCCESS:" + hits.toString());
                    for (int i = 0; i < hits.length(); i++) {
                        String isbn = hits.getJSONObject(i).getString("isbn");
                        Log.d("JSON", "SUCCESS:" + isbn);
                        int finalI = i;
                        Book book = new Book(isbn, new Book.OnLoadCallback() {
                            @Override
                            public void onLoadComplete(Book book) {
                                books.add(book);
                                //If it is the last book, call the callback
                                if(finalI == hits.length() - 1){
                                    searchCallback.onSearchBooks(books);
                                }
                            }
                        });
                    }

                } catch (JSONException e) {
                    //TODO ERROR HANDLING
                    e.printStackTrace();
                }
            }
        });
    }



    public interface SearchCallback {
        void onSearchBooks(ArrayList<Book> bookList);
    }
}

package com.example.boket.model;

import com.example.boket.helpers.algolia.Algolia;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Search {

    private static final String bookIndex = "BOOKINDEX";

    public Search() {
    }

    public static void searchBooks(String query, SearchCallback searchCallback){
        Algolia algolia = new Algolia(bookIndex);
        ArrayList<Book> books = new ArrayList<Book>();
        algolia.search(query, new Algolia.AlgoliaCallback() {
            @Override
            public void onSearchComplete(JSONObject content) {
                Iterator<String> keys = content.keys();
                while (keys.hasNext()){
                    String key = keys.next();
                    Book book = new Book(key);
                    books.add(book);
                }
                searchCallback.onSearchBooks(books);
            }
        });

    }

    public interface SearchCallback{
        void onSearchBooks(ArrayList<Book> bookList);
    }
}
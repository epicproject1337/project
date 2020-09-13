package com.example.boket.ui.search;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {

    private ArrayList<String> books;

    public ArrayList<String> fetchBooks(String input) { //Change type of list to Book object
        books = null; //fetch from database

        return books;
    }

    public ArrayList<String> getBooks(){
        return books;
    }

    // TODO: Implement the ViewModel



}
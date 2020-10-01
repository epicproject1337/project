package com.example.boket.ui.search;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.boket.model.Book;
import com.example.boket.ui.camera.BarcodeScannerActivity;

import java.util.ArrayList;

/**
 * @author Oscar Bennet
 *
 * View Model of SearchFragment
 * Stores books
 *
 * @since 2020-09-10
 */

public class SearchViewModel extends ViewModel {

    private ArrayList<Book> books;

    public ArrayList<Book> getBooks(){
        return books;
    }

    public void setBooks(ArrayList<Book> books){
        this.books = books;
    }

    // TODO: Implement the ViewModel

}
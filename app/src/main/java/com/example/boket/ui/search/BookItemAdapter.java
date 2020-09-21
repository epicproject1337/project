package com.example.boket.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;

import java.util.ArrayList;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemHolder>{

    Context c;
    ArrayList<BookItem> bookItems;

    public BookItemAdapter(Context c, ArrayList<BookItem> bookItems) {
        this.c = c;
        this.bookItems = bookItems;
    }

    @NonNull
    @Override
    public BookItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_book_item, null);
        return new BookItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookItemHolder holder, int i) {
        //holder.state.setText(bookItems.get(i).getState());

    }

    @Override
    public int getItemCount() {
        return bookItems.size();
    }

}

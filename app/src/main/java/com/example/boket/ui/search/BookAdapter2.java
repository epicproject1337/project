package com.example.boket.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;

import java.util.ArrayList;

public class BookAdapter2 extends RecyclerView.Adapter<ABookSellerHolder> {

    Context c;
    ArrayList<ABookSeller> bookSellers;

    public BookAdapter2(Context c, ArrayList<ABookSeller> bookSellers) {
        this.c = c;
        this.bookSellers = bookSellers;
    }

    @NonNull
    @Override
    public ABookSellerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_abookseller, null);
        return new ABookSellerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ABookSellerHolder holder, int i) {
        holder.state.setText(bookSellers.get(i).getState());
        holder.price.setText(bookSellers.get(i).getPrice());
        holder.city.setText(bookSellers.get(i).getCity());

    }

    @Override
    public int getItemCount() {
        return bookSellers.size();
    }
}

package com.example.boket.ui.profile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;
import com.example.boket.model.Book;
import com.example.boket.ui.RecyclerViewClickListener;
import com.example.boket.ui.search.BookItem;

import java.util.ArrayList;
import java.util.Random;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Context context;
    static RecyclerViewClickListener itemListener;
    private ArrayList<Book> bookItems;

    public ItemAdapter(Context context, RecyclerViewClickListener itemListener, ArrayList<Book> bookItems) {
        this.context = context;
        this.bookItems = bookItems;
        this.itemListener = itemListener;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.horizontal_list_item;
    }

    public Book getItem(int position){
        return bookItems.get(position);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Drawable d = ResourcesCompat.getDrawable(context.getResources(), R.drawable.diskmatte, null);
        holder.getView().setImageDrawable(d);
    }

    @Override
    public int getItemCount() {
        return bookItems.size();
    }

}

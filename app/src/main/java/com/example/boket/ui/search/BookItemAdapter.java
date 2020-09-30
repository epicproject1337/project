package com.example.boket.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.boket.R;
import com.example.boket.ui.RecyclerViewClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.BookItemHolder>{

    private Context c;
    private ArrayList<BookItem> bookItems;
    private static RecyclerViewClickListener itemListener;

    public BookItemAdapter(Context c, RecyclerViewClickListener itemListener, ArrayList<BookItem> bookItems) {
        this.c = c;
        this.bookItems = bookItems;
        this.itemListener = itemListener;
    }

    public BookItem getItem(int position){
        return bookItems.get(position);
    }


    @NonNull
    @Override
    public BookItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_book_item, null);
        return new BookItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookItemHolder holder, int i) {
        holder.bookTitle.setText(bookItems.get(i).getBookTitle());
        holder.author.setText(bookItems.get(i).getAuthor());
        holder.publishedYear.setText(bookItems.get(i).getPublishedYear());
        Glide.with(c).load(bookItems.get(i).getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bookItems.size();
    }

    public static class BookItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView bookTitle;
        TextView author;
        TextView publishedYear;
        ImageView imageView;


        public BookItemHolder(@NonNull View v) {
            super(v);

            this.bookTitle = v.findViewById(R.id.bookTitle);
            this.author = v.findViewById(R.id.author);
            this.publishedYear = v.findViewById(R.id.publishedYear);
            this.imageView = v.findViewById(R.id.imageView2);

            v.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }
}



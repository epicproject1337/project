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

import java.util.ArrayList;

/**
 * @author Oscar Bennet
 *
 * Adapter for book item so it can be used in recycler view
 *
 * @since 2020-09-21
 */

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


    /**
     * Binds xml file to BookItemHolder
     * @param parent
     * @param viewType
     * @return holder for the book item
     */
    @NonNull
    @Override
    public BookItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, null);
        return new BookItemHolder(v);
    }

    /**
     * Set text in holder
     * @param holder The holder for the book items
     * @param i Index of the book item in the recycler view
     */
    @Override
    public void onBindViewHolder(@NonNull BookItemHolder holder, int i) {
        holder.bookTitle.setText(bookItems.get(i).getBookTitle());
        holder.author.setText(bookItems.get(i).getAuthor());
        holder.publishedYear.setText(bookItems.get(i).getPublishedYear());
        Glide.with(c).load(bookItems.get(i).getImage()).into(holder.imageView);
    }

    /**
     * Get amount of book items in list
     * @return amount of book items in list
     */
    @Override
    public int getItemCount() {
        return bookItems.size();
    }

    /**
     * @author Oscar Bennet
     *
     * Holder for book item so it can be used in adapter which is being used in recycler view
     *
     * @since 2020-09-21
     */
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


        /**
         * Handle clicks on book item
         * @param v
         */
        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }
}



package com.example.boket.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.boket.R;
import com.example.boket.model.Book;
import com.example.boket.ui.RecyclerViewClickListener;

import java.util.ArrayList;

/**
 * Manages the RecyclerViews used for showing books on the profile page.
 *
 * @author Albin Landgren
 * @since 2020-09-10
 */

public class SubscribedBookAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private Context context;
    static RecyclerViewClickListener itemListener;
    private ArrayList<Book> bookItems;


    /**
     * @param context
     * @param itemListener ClickListener to enable onclick events
     * @param bookItems List of Book model instances
     */
    public SubscribedBookAdapter(Context context, RecyclerViewClickListener itemListener, ArrayList<Book> bookItems) {
        this.context = context;
        this.bookItems = bookItems;
        this.itemListener = itemListener;
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(final int position) {
        return R.layout.horizontal_list_item;
    }

    /**
     * Retrieve instance of Book at given position
     *
     * @param position the instance's position in the collection
     * @return
     */
    public Book getItem(int position){
        return bookItems.get(position);
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view, true, itemListener);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Glide.with(context).load(bookItems.get(position).getImage()).into(holder.getView());
    }

    /**
     *
     * @return the amount of RecyclerView items
     */
    @Override
    public int getItemCount() {
        return bookItems.size();
    }

}

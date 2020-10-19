package com.example.boket.ui.profile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.boket.R;
import com.example.boket.model.Ad;
import com.example.boket.model.Book;
import com.example.boket.ui.RecyclerViewClickListener;
import com.example.boket.ui.search.BookItem;

import java.util.ArrayList;
import java.util.Random;

/**
 * Manages the RecyclerViews used for showing books on the profile page.
 *
 * @author Albin Landgren
 * @since 2020-09-10
 */

public class ManageAdAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements RecyclerViewClickListener{
    private Context context;
    private ArrayList<Ad> ads;


    /**
     * @param context
     * @param ads List of Book model instances
     */
    public ManageAdAdapter(Context context, RecyclerViewClickListener listener, ArrayList<Ad> ads) {
        this.context = context;
        this.ads = ads;
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
    public Ad getItem(int position){
        return ads.get(position);
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
        return new RecyclerViewHolder(view, false, this);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Glide.with(context).load(ads.get(position).getImageUrl()).into(holder.getView());
    }

    /**
     *
     * @return the amount of RecyclerView items
     */
    @Override
    public int getItemCount() {
        return ads.size();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Ad ad = ads.get(position);
        ad.archiveAd();
    }
}

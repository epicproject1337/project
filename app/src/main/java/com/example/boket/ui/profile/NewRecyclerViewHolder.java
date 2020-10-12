package com.example.boket.ui.profile;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;
import com.example.boket.ui.RecyclerViewClickListener;

import androidx.annotation.NonNull;

import static com.example.boket.ui.profile.SubscribedBookAdapter.itemListener;

/**
 * Represents an item in the RecyclerView on the profile page
 *
 * @author Albin Landgren
 * @since 2020-09-10
 */
public class NewRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView view;

    /**
     *
     * @param itemView
     */
    public NewRecyclerViewHolder(@NonNull View itemView, boolean clickable) {
        super(itemView);
        if (clickable) {
            view = itemView.findViewById(R.id.bookimage);
            RecyclerView.ViewHolder that = this;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemListener.recyclerViewListClicked(view, that.getLayoutPosition());
                }
            });
        }
    }

    /**
     *
     * @return the ImageView for this RecyclerView item.
     */
    public ImageView getView() {
        return view;
    }

    /**
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        //itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}

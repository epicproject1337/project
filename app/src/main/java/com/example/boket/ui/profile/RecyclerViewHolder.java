package com.example.boket.ui.profile;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

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
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView view;
    private ImageButton dotsButton;
    private RecyclerViewClickListener dotsButtonClickListener;

    /**
     *
     * @param itemView
     */
    public RecyclerViewHolder(@NonNull View itemView, boolean clickable, RecyclerViewClickListener dotsButtonClickListener) {
        super(itemView);
        view = itemView.findViewById(R.id.bookimage);
        dotsButton = itemView.findViewById(R.id.dotsButton);
        if (clickable) {
            dotsButton.setVisibility(View.GONE);
            RecyclerView.ViewHolder that = this;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemListener.recyclerViewListClicked(view, that.getLayoutPosition());
                }
            });
        } else {
            this.dotsButtonClickListener = dotsButtonClickListener;
            dotsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                default:
                                    markAsSold();
                                    return true;
                            }
                        }
                    });
                    MenuInflater inflater =  popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.manage_ad_menu, popupMenu.getMenu());
                    popupMenu.show();
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

    public void markAsSold() {
        dotsButtonClickListener.recyclerViewListClicked(itemView, this.getLayoutPosition());
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

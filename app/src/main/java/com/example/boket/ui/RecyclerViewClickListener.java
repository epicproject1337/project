package com.example.boket.ui;

import android.view.View;

/**
 * @author Oscar Bennet
 *
 * Interface for handling clicked items in recycler view
 *
 * @since 2020-09-10
 */

public interface RecyclerViewClickListener {
    public void recyclerViewListClicked(View v, int position);
}
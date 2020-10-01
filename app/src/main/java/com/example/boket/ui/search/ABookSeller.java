package com.example.boket.ui.search;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.boket.R;

/**
 * @author Tarik Porobic
 *
 * Class that represent a bookseller for a specific book
 * @since 2020-10-01
 */
public class ABookSeller {

    private String state, price, city;
    private ImageButton contactSeller;
    private ConstraintLayout expandableLayout;
    private CardView cardView;


    public ABookSeller(String state, String price, String city, View v) {
        this.state = state;
        this.price = price;
        this.city = city;
        this.contactSeller = v.findViewById(R.id.contactSellerBtn);
        this.expandableLayout = v.findViewById(R.id.expandableView);
        this.cardView = v.findViewById(R.id.cardView);
    }

    /*
    public ConstraintLayout getExpandableLayout() {
        return expandableLayout;
    }

     */

    /**
     * Returns the value so that the holder can present this information
     * @return state of the book
     */
    public String getState() {
        return state;
    }

    /**
     * Returns the value so that the holder can present this information
     * @return price of the book
     */
    public String getPrice() {
        return price;
    }

    /**
     * Returns the value so that the holder can present this information
     * @return the city where the book is sold at
     */
    public String getCity() {
        return city;
    }


}

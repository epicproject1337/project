package com.example.boket.ui.search;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.boket.R;

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


    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public ConstraintLayout getExpandableLayout() {
        return expandableLayout;
    }

    public void setExpandableLayout(ConstraintLayout expandableLayout) {
        this.expandableLayout = expandableLayout;
    }

    public ImageButton getContactSeller() {
        return contactSeller;
    }

    public void setContactSeller(ImageButton contactSeller) {
        this.contactSeller = contactSeller;
    }

    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

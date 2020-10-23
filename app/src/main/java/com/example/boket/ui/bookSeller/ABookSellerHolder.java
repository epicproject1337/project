package com.example.boket.ui.bookSeller;

import android.os.Build;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;

/**
 * @author Tarik Porobic
 * <p>
 * A holder for AbookSeller class so that it can be used in adapter for the recyclerview in
 * BooksellersFragement
 * @since 2020-09-24
 */
public class ABookSellerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView state, price, city;
    private ImageButton contactSeller;
    private ConstraintLayout expandableLayout;
    private CardView cardView;

    private IABookSellerCL iaBookSellerCL;

    /**
     * It is package private since it is only needed for the BookAdapter
     *
     * @param v view that the holder is being bond to
     */
    ABookSellerHolder(@NonNull View v) {
        super(v);

        this.state = v.findViewById(R.id.bookState);
        this.price = v.findViewById(R.id.price);
        this.city = v.findViewById(R.id.city);
        this.contactSeller = v.findViewById(R.id.contactSellerBtn);
        this.expandableLayout = v.findViewById(R.id.expandableView);
        this.cardView = v.findViewById(R.id.cardView);

        contactSeller.setOnClickListener(this);
        v.setOnClickListener(this);
    }

    /**
     * Deligates all the click methods for ABookSellerHolder
     *
     * @param view of fragment_abookseller with the expanded/contracted "expandableLayout"
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contactSellerBtn:
                this.iaBookSellerCL.onIABookSellerBtnCL(getLayoutPosition());
                break;
            case R.id.book:
                this.iaBookSellerCL.onIABookSellerCL(view, getLayoutPosition());
                break;
        }
/*
        if (view.getId() == R.id.contactSellerBtn) {
            this.iaBookSellerCL.onIABookSellerBtnCL(getLayoutPosition());
        } else {
            this.iaBookSellerCL.onIABookSellerCL(view, getLayoutPosition());
        }

 */

    }

    /**
     * Setter for iaBookSellerCL
     *
     * @param IaBCL
     */
    public void setIaBookSellerCL(IABookSellerCL IaBCL) {
        this.iaBookSellerCL = IaBCL;
    }

    /**
     * @return state of the book
     */
    public TextView getState() {
        return state;
    }

    /**
     * @return price of the book
     */
    public TextView getPrice() {
        return price;
    }

    /**
     * @return the city where the book is being sold
     */
    public TextView getCity() {
        return city;
    }

    public ConstraintLayout getExpandableLayout() {
        return expandableLayout;
    }

    public CardView getCardView() {
        return cardView;
    }

}

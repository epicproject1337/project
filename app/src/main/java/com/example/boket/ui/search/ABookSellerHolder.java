package com.example.boket.ui.search;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;

public class ABookSellerHolder extends RecyclerView.ViewHolder {

    TextView state, price, city;

    public ABookSellerHolder(@NonNull View v) {
        super(v);

        this.state = v.findViewById(R.id.bookState);
        this.price = v.findViewById(R.id.price);
        this.city = v.findViewById(R.id.city);

    }
}

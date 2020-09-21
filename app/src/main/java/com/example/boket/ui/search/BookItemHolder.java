package com.example.boket.ui.search;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;

public class BookItemHolder extends RecyclerView.ViewHolder{
    TextView text;

    public BookItemHolder(@NonNull View v) {
        super(v);

        this.text = v.findViewById(R.id.bookState);


    }

}

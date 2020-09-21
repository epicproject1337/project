package com.example.boket.ui.search;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;

public class BookItemHolder extends RecyclerView.ViewHolder{
    TextView bookTitle;
    TextView author;
    TextView publishedYear;

    public BookItemHolder(@NonNull View v) {
        super(v);

        this.bookTitle = v.findViewById(R.id.bookTitle);
        this.author = v.findViewById(R.id.author);
        this.publishedYear = v.findViewById(R.id.publishedYear);


    }

}

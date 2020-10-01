package com.example.boket.ui.profile;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.MainActivity;
import com.example.boket.R;

import java.util.List;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.boket.ui.profile.ItemAdapter.itemListener;

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView view;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.bookimage);
        RecyclerView.ViewHolder that = this;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.recyclerViewListClicked(view, that.getLayoutPosition());
            }
        });
    }



    public ImageView getView() {
        return view;
    }

    @Override
    public void onClick(View view) {
        //itemListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}

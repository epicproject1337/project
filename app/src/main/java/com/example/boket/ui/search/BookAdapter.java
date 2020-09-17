package com.example.boket.ui.search;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private List<String> data;

    public BookAdapter(Context contex, List<String> data ){
        this.layoutInflater=layoutInflater.from(contex);
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_abookseller,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Bind TextView with data received
        String name = data.get(position);
        holder.bookName.setText(name);
         /*
        String name = "book namn 123";
        holder.bookName.setText(name);

          */
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView bookName, bookAuthor;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           bookName=itemView.findViewById(R.id.bookName);
           bookAuthor=itemView.findViewById(R.id.bookAuthor);
       }
   }
}

package com.example.boket.ui.search;

import android.content.Context;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;

import java.util.ArrayList;

import io.grpc.internal.JsonUtil;

public class BookAdapter extends RecyclerView.Adapter<ABookSellerHolder> {

    Context c;
    ArrayList<ABookSeller> bookSellers;

    public BookAdapter(Context c, ArrayList<ABookSeller> bookSellers) {
        this.c = c;
        this.bookSellers = bookSellers;
    }

    @NonNull
    @Override
    public ABookSellerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_abookseller, null);
        return new ABookSellerHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull ABookSellerHolder holder, int i) {
        System.out.println("här 2");

        holder.setIaBookSellerCL(new IABookSellerCL() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onIABookSellerCL(View v, int i) {
                ABookSeller bS = bookSellers.get(i);
                System.out.println("börjar");

/*

                if(bS.getExpandableLayout().getVisibility()==View.GONE){
                    TransitionManager.beginDelayedTransition(bS.getCardView(),new AutoTransition());
                    bS.getExpandableLayout().setVisibility(View.VISIBLE);
                }else{
                    TransitionManager.beginDelayedTransition(bS.getCardView(),new AutoTransition());
                    bS.getExpandableLayout().setVisibility(View.GONE);
                }



 */

            }
        });

        System.out.println("klar");
        holder.state.setText(bookSellers.get(i).getState());
        holder.price.setText(bookSellers.get(i).getPrice());
        holder.city.setText(bookSellers.get(i).getCity());


    }

    @Override
    public int getItemCount() {
        return bookSellers.size();
    }
}

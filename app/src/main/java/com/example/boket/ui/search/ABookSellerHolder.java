package com.example.boket.ui.search;

import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;

public class ABookSellerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView state, price, city;
    ImageButton contactSeller;
    ConstraintLayout expandableLayout;
    CardView cardView;

    IABookSellerCL iaBookSellerCL;


    ABookSellerHolder(@NonNull View v) {
        super(v);

        this.state = v.findViewById(R.id.bookState);
        this.price = v.findViewById(R.id.price);
        this.city = v.findViewById(R.id.city);
        this.contactSeller=v.findViewById(R.id.contactSellerBtn);
        this.expandableLayout=v.findViewById(R.id.expandableView);
        this.cardView=v.findViewById(R.id.cardView);



        v.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void changeVisibility(){
        if(expandableLayout.getVisibility()==View.GONE){
            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
            expandableLayout.setVisibility(View.VISIBLE);
        }else{
            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
            expandableLayout.setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        System.out.println("här");
        this.changeVisibility();
        this.iaBookSellerCL.onIABookSellerCL(view,getLayoutPosition());
        System.out.println("här passerat");
    }

    public void setIaBookSellerCL(IABookSellerCL AbCL){
        this.iaBookSellerCL=AbCL;
    }
}

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boket.R;

import java.util.ArrayList;

import io.grpc.internal.JsonUtil;

/**
 * @author Tarik Porobic
 * Adapter for bookSeller so that it can be presented in the recyclerview in BooksellersFragment
 *
 * @since 2020-01-01
 */
public class BookAdapter extends RecyclerView.Adapter<ABookSellerHolder> {

    private Context c;
    private ArrayList<ABookSeller> bookSellers;

    /**
     *
     * @param c Parent of the  BooksellersFragment view
     * @param bookSellers List with bookSellers that will be presented in recyclerview
     */
    public BookAdapter(Context c, ArrayList<ABookSeller> bookSellers) {
        this.c = c;
        this.bookSellers = bookSellers;
    }

    /**
     * Binds the view "fragmen_abookseller" to a holder
     * @param parent Android parameter
     * @param viewType Android parameter
     * @return A holder with a view
     */
    @NonNull
    @Override
    public ABookSellerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_abookseller, null);
        return new ABookSellerHolder(v);
    }

    /**
     * Sets right information on the holder
     * @param holder that will go in the recyclerview
     * @param i position in the recyclerview
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull ABookSellerHolder holder, int i) {
        ;
        holder.setIaBookSellerCL(new IABookSellerCL() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onIABookSellerCL(View v, int i) {
                /*
                ABookSeller bS = bookSellers.get(i);
                System.out.println("börjar");
                for (int pos = 0; i<bookSellers.size();i++){
                    if (pos==i){
                        pos++;
                        if (pos==bookSellers.size()){
                            break;
                        }
                    }
                    ConstraintLayout cv = bookSellers.get(pos).getExpandableLayout();
                    cv.setVisibility(View.INVISIBLE);
                }



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
        holder.getState().setText(bookSellers.get(i).getState());
        holder.getPrice().setText(bookSellers.get(i).getPrice());
        holder.getCity().setText(bookSellers.get(i).getCity());



    }

    /**
     *
     * @return The size of the list that will be in recyclerview
     */
    @Override
    public int getItemCount() {
        return bookSellers.size();
    }
}

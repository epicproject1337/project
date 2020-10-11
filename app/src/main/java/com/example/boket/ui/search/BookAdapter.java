package com.example.boket.ui.search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.boket.model.Ad;

import java.util.ArrayList;

import io.grpc.internal.JsonUtil;

/**
 * @author Tarik Porobic
 * Adapter for bookSeller so that it can be presented in the recyclerview in BooksellersFragment
 * @since 2020-09-24
 */
public class BookAdapter extends RecyclerView.Adapter<ABookSellerHolder> {

    private RecyclerView rV;
    private ArrayList<ABookSeller> bookSellers;
    private Context c;

    /**
     * @param c           context of BooksellersFragment
     * @param bookSellers List with bookSellers that will be presented in recyclerview
     */
    public BookAdapter(Context c, ArrayList<ABookSeller> bookSellers) {
        this.bookSellers = bookSellers;
        this.c = c;
    }

    /**
     * Binds the view "fragmen_abookseller" to a holder
     *
     * @param parent   Android parameter
     * @param viewType Android parameter
     * @return A holder with a view
     */
    @NonNull
    @Override
    public ABookSellerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_abookseller, null);
        ABookSellerHolder ABsH = new ABookSellerHolder(v);
        //rV.add(ABsH);
        //System.out.println(rV.size());
        return ABsH;
    }

    /**
     * Sets right information on the holder
     *
     * @param holder that will go in the recyclerview
     * @param i      position in the recyclerview
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull ABookSellerHolder holder, int i) {
        holder.setIaBookSellerCL(new IABookSellerCL() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onIABookSellerCL(View v, int i) {
                ABookSellerHolder vh = (ABookSellerHolder) rV.findViewHolderForAdapterPosition(i);
                //ABookSellerHolder vh = rV.get(i);
                assert vh != null;
                if (vh.getExpandableLayout().getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(vh.getCardView(), new AutoTransition());
                    vh.getExpandableLayout().setVisibility(View.VISIBLE);

                    closeTheOther(i);

                } else {
                    System.out.println("måste kommma hit");
                    TransitionManager.beginDelayedTransition(vh.getCardView(), new AutoTransition());
                    vh.getExpandableLayout().setVisibility(View.GONE);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onIABookSellerBtnCL(int i) {
/*
                //Intent intent = new Intent(Intent.ACTION_SEND);
                //intent.setData(Uri.parse("riktapro@gmail.com"));//Till eller Från
                //intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                intent.putExtra(Intent.EXTRA_CC, new String[]{"tarik.porobic@outlook.com"});//Till eller Från
                intent.putExtra(Intent.EXTRA_BCC, new String[]{"tarik.porobic@outlook.com"});//Till eller från
                intent.putExtra(Intent.EXTRA_SUBJECT, "Jag skulle vilja köpa din bok, ");
                intent.putExtra(Intent.EXTRA_TEXT, "Hej! \n" + "Jag är intresserad utav att köpa din bok, ");


 */
                String[] recipients = new String[1];
                ABookSeller b = bookSellers.get(i);
                recipients[0] = b.getSellerEmail();


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Jag skulle vilja köpa din bok, " + b.getBookSold());
                intent.putExtra(Intent.EXTRA_TEXT, "Hej!\n\n" + "Jag såg din bokannons på den underbara appen " +
                        "Booket och skulle vilja köpa, " + b.getBookSold() + "\n\nMed vänlig hälsning\n/" + "Booket men ska vara ett namn här");
                intent.setPackage("com.google.android.gm");
                c.startActivity(intent);

            }
        });
        holder.getState().setText(bookSellers.get(i).getState());
        holder.getPrice().setText(bookSellers.get(i).getPrice());
        holder.getCity().setText(bookSellers.get(i).getCity());


    }

    private void closeTheOther(int i) {
        for (int pos = 0; pos < bookSellers.size(); pos++) {
            if (pos != i) {
                ABookSellerHolder vh = (ABookSellerHolder) rV.findViewHolderForAdapterPosition(pos);
                //ABookSellerHolder vh = rV.get(i);
                assert vh != null;
                vh.getExpandableLayout().setVisibility(View.GONE);
            }
        }
    }

    /**
     * @return The size of the list that will be in recyclerview
     */
    @Override
    public int getItemCount() {
        return bookSellers.size();
    }

    public void setrV(RecyclerView rV) {
        this.rV = rV;
    }


}

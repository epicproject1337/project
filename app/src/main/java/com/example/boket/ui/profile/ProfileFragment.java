package com.example.boket.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.boket.R;

public class ProfileFragment extends Fragment {

    private RecyclerView buyAds;
    private RecyclerView sellAds;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment

        // Add the following lines to create RecyclerView
        buyAds = view.findViewById(R.id.buyAds);
        buyAds.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        buyAds.setLayoutManager(layoutManager);
        buyAds.setAdapter(new ItemAdapter(view.getContext()));

        sellAds = view.findViewById(R.id.sellAds);
        sellAds.setHasFixedSize(true);
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        sellAds.setLayoutManager(layoutManager2);
        sellAds.setAdapter(new ItemAdapter(view.getContext()));
        return view;
    }
}
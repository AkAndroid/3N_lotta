package com.a3nlotta.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;
import com.a3nlotta.viewHolder.DrawViewHolder;
import com.a3nlotta.viewHolder.WinnersViewHolder;

public class WinnersListAdapter extends RecyclerView.Adapter<WinnersViewHolder> {
    @NonNull
    @Override
    public WinnersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.winner_item_layout,parent,false);
        return new WinnersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WinnersViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}

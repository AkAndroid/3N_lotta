package com.a3nlotta.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;
import com.a3nlotta.viewHolder.RulesItemViewHolder;

public class RulesAdapter extends RecyclerView.Adapter<RulesItemViewHolder> {
    @NonNull
    @Override
    public RulesItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rules_item_layout,parent,false);
        return new RulesItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RulesItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}

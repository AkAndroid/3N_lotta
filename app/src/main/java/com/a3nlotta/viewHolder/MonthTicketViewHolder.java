package com.a3nlotta.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MonthTicketViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvDrawName)
    public TextView tvDrawName;
    @BindView(R.id.tvDrawDesc)
    public TextView tvDrawDesc;
    @BindView(R.id.tvDrawAmount)
    public TextView tvDrawAmount;
    @BindView(R.id.tvTicketNo)
    public TextView tvTicketNo;
    @BindView(R.id.ivSold)
    public ImageView ivSold;


    public MonthTicketViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}

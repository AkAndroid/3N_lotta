package com.a3nlotta.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;

public class DrawViewHolder extends RecyclerView.ViewHolder {

    public ConstraintLayout conLayDraw;
    public TextView tvMsg;
    public TextView tvDraw;
    public TextView tvAmount;
    public TextView tvEntryFee;
    public TextView tvDate;
    public TextView tvHowToPlay;
    public Button btStart;

    public DrawViewHolder(@NonNull View itemView) {
        super(itemView);
        conLayDraw=itemView.findViewById(R.id.conLayDraw);
        tvDraw=itemView.findViewById(R.id.tvDraw);
        tvMsg=itemView.findViewById(R.id.tvMsg);
        tvAmount=itemView.findViewById(R.id.tvAmount);
        tvEntryFee=itemView.findViewById(R.id.tvEntryFee);
        tvDate=itemView.findViewById(R.id.tvDate);
        btStart=itemView.findViewById(R.id.btStart);
        tvHowToPlay=itemView.findViewById(R.id.tvHowToPlay);
    }
}

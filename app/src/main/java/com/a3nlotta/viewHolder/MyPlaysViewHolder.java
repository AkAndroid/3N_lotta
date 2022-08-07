package com.a3nlotta.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;

public class MyPlaysViewHolder extends RecyclerView.ViewHolder {
    public TextView tvPlayName;
    public TextView tvDate;
    public TextView tvEntryFee;
    public TextView tvRank;
    public TextView tvLoss;
    public TextView tvWin;
    public TextView tvStatus;
    public MyPlaysViewHolder(@NonNull View itemView) {
        super(itemView);

        tvPlayName=itemView.findViewById(R.id.tvPlayName);
        tvDate=itemView.findViewById(R.id.tvDate);
        tvEntryFee=itemView.findViewById(R.id.tvEntryFee);
        tvRank=itemView.findViewById(R.id.tvRank);
        tvLoss=itemView.findViewById(R.id.tvLoss);
        tvWin=itemView.findViewById(R.id.tvWin);
        tvStatus=itemView.findViewById(R.id.tvStatus);
    }
}

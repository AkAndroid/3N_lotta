package com.a3nlotta.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;

public class PaymentHistoryViewHolder extends RecyclerView.ViewHolder {

    public TextView tvAccountNo;
    public TextView tvDate;
    public TextView tvStatus;
    public TextView tvAmount;
    public ImageView ivStatus;

    public PaymentHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvAccountNo=itemView.findViewById(R.id.tvAccountNo);
        tvDate=itemView.findViewById(R.id.tvDate);
        tvStatus=itemView.findViewById(R.id.tvStatus);
        tvAmount=itemView.findViewById(R.id.tvAmount);
        ivStatus=itemView.findViewById(R.id.ivStatus);
    }
}

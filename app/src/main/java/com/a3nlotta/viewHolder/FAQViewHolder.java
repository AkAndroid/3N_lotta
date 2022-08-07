package com.a3nlotta.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;

public class FAQViewHolder extends RecyclerView.ViewHolder {

    public TextView tvFAQTitle;
    public TextView tvDesc;
    public FAQViewHolder(@NonNull View itemView) {
        super(itemView);
        tvFAQTitle=itemView.findViewById(R.id.tvFAQTitle);
        tvDesc=itemView.findViewById(R.id.tvDesc);
    }
}

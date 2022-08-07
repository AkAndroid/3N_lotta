package com.a3nlotta.viewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;

public class PrivacyPolicyViewHolder extends RecyclerView.ViewHolder {


    public TextView tvTitle;
    public TextView tvDesc;

    public PrivacyPolicyViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTitle=itemView.findViewById(R.id.tvTitle);
        tvDesc=itemView.findViewById(R.id.tvDesc);
    }
}

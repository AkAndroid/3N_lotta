package com.a3nlotta.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HowToPlayAndNoteViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvDetails)
    public TextView tvDetails;
    @BindView(R.id.tvNo)
    public TextView tvNo;

    public HowToPlayAndNoteViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}

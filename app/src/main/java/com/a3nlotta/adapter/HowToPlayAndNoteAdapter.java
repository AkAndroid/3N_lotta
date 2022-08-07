package com.a3nlotta.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;
import com.a3nlotta.model.draw.HowToPlay;
import com.a3nlotta.model.mayplay.MyPlaysData;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.viewHolder.HowToPlayAndNoteViewHolder;
import com.a3nlotta.viewHolder.MyPlaysViewHolder;

import java.util.List;

public class HowToPlayAndNoteAdapter extends RecyclerView.Adapter<HowToPlayAndNoteViewHolder> {
    private final Context context;
    private final List<HowToPlay> data;

    public HowToPlayAndNoteAdapter(Context context, List<HowToPlay> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public HowToPlayAndNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.how_to_play_item,parent,false);
        return new HowToPlayAndNoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HowToPlayAndNoteViewHolder holder, int position) {
        if(data.get(position)!=null){
            HowToPlay howToPlay = data.get(position);

            holder.tvNo.setText(String.valueOf(position+1));

            if(!TextUtils.isEmpty(howToPlay.getDescription()))
                holder.tvDetails.setText(howToPlay.getDescription());

        }
    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }
}

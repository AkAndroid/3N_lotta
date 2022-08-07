package com.a3nlotta.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;
import com.a3nlotta.activity.MyPlaysActivity;
import com.a3nlotta.model.mayplay.MyPlaysData;
import com.a3nlotta.model.mayplay.MyPlaysModel;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.SharedPreferencesManager;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.viewHolder.MyPlaysViewHolder;

import java.util.List;

public class MyPalysAdapter extends RecyclerView.Adapter<MyPlaysViewHolder> {
    private final Context context;
    private final List<MyPlaysData> data;

    public MyPalysAdapter(Context context, List<MyPlaysData> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public MyPlaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.my_plays_item,parent,false);
        return new MyPlaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPlaysViewHolder holder, int position) {
        if(data.get(position)!=null){
            MyPlaysData myPlays = data.get(position);

            if(!TextUtils.isEmpty(myPlays.getDrawName()))
                holder.tvPlayName.setText(myPlays.getDrawName());
            if(!TextUtils.isEmpty(myPlays.getFees()))
                holder.tvEntryFee.setText(myPlays.getFees()+ SharedPreferencesManager.getCurrency());
            if(!TextUtils.isEmpty(myPlays.getRank()))
                holder.tvRank.setText(myPlays.getRank());
            if(!TextUtils.isEmpty(myPlays.getOrderStatus()))
                holder.tvStatus.setText(myPlays.getOrderStatus());
            if(!TextUtils.isEmpty(myPlays.getWinPrice()))
                holder.tvWin.setText(myPlays.getWinPrice()+SharedPreferencesManager.getCurrency());
            if(!TextUtils.isEmpty(myPlays.getLoss()))
                holder.tvLoss.setText(myPlays.getLoss()+SharedPreferencesManager.getCurrency());
            if(!TextUtils.isEmpty(myPlays.getCreatedAt()))
                holder.tvDate.setText(Utilities.convertDate(myPlays.getCreatedAt(), Constants.dateFormat_YYYY_MM_DD_HH_MM_SS,Constants.dateFormat_DD_MM_YYYY));

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

package com.a3nlotta.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;
import com.a3nlotta.activity.HTMLTextActivity;
import com.a3nlotta.activity.HowToPlayActivity;
import com.a3nlotta.activity.LoginActivity;
import com.a3nlotta.activity.MonthDrawTicketActivity;
import com.a3nlotta.activity.RegisterDrawActivity;
import com.a3nlotta.model.draw.DrawModel;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.SharedPreferencesManager;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.viewHolder.DrawViewHolder;

import java.util.List;

public class DrawsAdapter extends RecyclerView.Adapter<DrawViewHolder> {
    private final Context context;
    private final List<DrawModel> draw;

    public DrawsAdapter(Context context, List<DrawModel> draw) {
        this.context=context;
        this.draw=draw;
    }

    @NonNull
    @Override
    public DrawViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.draws_item,parent,false);
        return new DrawViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawViewHolder holder, int position) {

        holder.conLayDraw.setBackgroundTintList(position%2 == 0?

        ColorStateList.valueOf(context.getResources().getColor(R.color.orange,null))
                :
                ColorStateList.valueOf(context.getResources().getColor(R.color.green,null))
        );
        if(draw.get(position)!=null){
            DrawModel drawModel =draw.get(position);
            if(drawModel.getDrawName().toLowerCase().contains("month")){
                holder.tvHowToPlay.setText(context.getString(R.string.how_to_win));
            }else {
                holder.tvHowToPlay.setText(context.getString(R.string.how_to_play_title));
            }

                holder.btStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (SharedPreferencesManager.isLogin()){
                            if(drawModel.getDrawName().toLowerCase().contains("month")){
                                Intent i = new Intent(context, MonthDrawTicketActivity.class);
                                context.startActivity(i);
                            }else {
                                Intent i = new Intent(context, RegisterDrawActivity.class);
                                i.putExtra("draw", drawModel);
                                context.startActivity(i);
                            }
                        }else{
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                    }
                });

            holder.tvHowToPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent i = new Intent(context, HowToPlayActivity.class);
                        i.putExtra("draw_name", drawModel.getDrawName());
                        context.startActivity(i);
                }
            });

            if(!TextUtils.isEmpty(drawModel.getDrawName()))
                holder.tvDraw.setText(drawModel.getDrawName());
            if(!TextUtils.isEmpty(drawModel.getEntryFee()))
                holder.tvEntryFee.setText(context.getResources().getString(R.string.entry_fees)+" "+drawModel.getEntryFee()+SharedPreferencesManager.getCurrency());
            if(!TextUtils.isEmpty(drawModel.getPrize()))
                holder.tvAmount.setText(drawModel.getPrize()+SharedPreferencesManager.getCurrency());
            if(!TextUtils.isEmpty(drawModel.getText()))
                holder.tvMsg.setText(drawModel.getText());
            /*if(!TextUtils.isEmpty(drawModel.getEndDate()))
                holder.tvDate.setText(Utilities.convertDate(drawModel.getEndDate(), Constants.dateFormat_YYYY_MM_DD_HH_MM_SS,Constants.dateFormat_D));*/
            if(!TextUtils.isEmpty(drawModel.getDays()))
                holder.tvDate.setText(drawModel.getDays());
        }
    }

    @Override
    public int getItemCount() {
        return draw!=null?draw.size():0;
    }
}

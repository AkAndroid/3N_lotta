package com.a3nlotta.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;
import com.a3nlotta.activity.RegisterDrawActivity;
import com.a3nlotta.model.home.tickets.TicketData;
import com.a3nlotta.viewHolder.MonthTicketViewHolder;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class MonthDrawTicketAdapter extends RecyclerView.Adapter<MonthTicketViewHolder> {
    private final Context context;
    private List<TicketData> items;

    public MonthDrawTicketAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public MonthTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.draw_ticket_item,parent,false);
        return new MonthTicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthTicketViewHolder holder, int position) {

        TicketData ticketData = items.get(position);
        if(ticketData!=null){

            if(!TextUtils.isEmpty(ticketData.getTicketName()))
                holder.tvDrawName.setText(ticketData.getTicketName());
            if(!TextUtils.isEmpty(ticketData.getDescription()))
                holder.tvDrawDesc.setText(ticketData.getDescription());
            if(!TextUtils.isEmpty(ticketData.getFee()))
                holder.tvDrawAmount.setText(ticketData.getFee()+" $");
            if(ticketData.getId()!=null)
                holder.tvTicketNo.setText("No "+ticketData.getId());

            if(ticketData.getStatus().equals("1")) {
                holder.ivSold.setVisibility(View.GONE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, RegisterDrawActivity.class);
                        i.putExtra("ticket", ticketData);
                        context.startActivity(i);
                    }
                });

            }else{
                holder.ivSold.setVisibility(View.VISIBLE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toasty.warning(context, R.string.tickets_already_sold).show();
                    }
                });

            }

        }
    }

    @Override
    public int getItemCount() {
        return items!=null?items.size():0;
    }

    public void setData(List<TicketData> data){
        if(items == null){
            items = data;
        }else{
            items.addAll(data);
        }

        notifyDataSetChanged();
    }

    public void clearData() {
        if(items != null){
            items.clear();
        }
    }
}

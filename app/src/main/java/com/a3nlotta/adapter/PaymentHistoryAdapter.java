package com.a3nlotta.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;
import com.a3nlotta.model.wallet.WithdrawModel;
import com.a3nlotta.utils.SharedPreferencesManager;
import com.a3nlotta.viewHolder.PaymentHistoryViewHolder;

import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryViewHolder> {
    private final Context context;
    private final List<WithdrawModel> data;

    public PaymentHistoryAdapter(Context context, List<WithdrawModel> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public PaymentHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.payment_history_item,parent,false);

        return new PaymentHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHistoryViewHolder holder, int position) {
        if(data.get(position)!=null){
            WithdrawModel withdrawModel = data.get(position);
            if(!TextUtils.isEmpty(withdrawModel.getWithdrawAmount()))
                holder.tvAmount.setText(withdrawModel.getWithdrawAmount()+ SharedPreferencesManager.getCurrency());

            if(!TextUtils.isEmpty(withdrawModel.getAccountNumber()))
                holder.tvAccountNo.setText(withdrawModel.getAccountNumber());

            if(!TextUtils.isEmpty(withdrawModel.getCreatedAt()))
                holder.tvDate.setText(withdrawModel.getCreatedAt());
            if(!TextUtils.isEmpty(withdrawModel.getStatus())) {
                holder.tvDate.setText(withdrawModel.getStatus());
                if (withdrawModel.getStatus().contains("success")) {
                   holder.tvStatus.setTextColor(context.getColor(R.color.green_success));
                   holder.ivStatus.setImageDrawable(context.getDrawable(R.drawable.ic_check_circle));
                }else if (withdrawModel.getStatus().contains("failed")) {
                   holder.tvStatus.setTextColor(context.getColor(R.color.red));
                   holder.ivStatus.setImageDrawable(context.getDrawable(R.drawable.ic_close));
                }else{
                   holder.tvStatus.setTextColor(context.getColor(R.color.orange));
                   holder.ivStatus.setImageDrawable(context.getDrawable(R.drawable.ic_error));
                }
            }else{
                holder.tvDate.setText(R.string.pending);
                holder.tvStatus.setTextColor(context.getColor(R.color.orange));
                holder.ivStatus.setImageDrawable(context.getDrawable(R.drawable.ic_error));
            }
        }
    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }
}

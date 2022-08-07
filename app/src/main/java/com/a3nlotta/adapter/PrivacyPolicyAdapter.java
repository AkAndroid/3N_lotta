package com.a3nlotta.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;
import com.a3nlotta.model.privacypolicy.PrivacyPolicyData;
import com.a3nlotta.viewHolder.PrivacyPolicyViewHolder;

import java.util.List;

public class PrivacyPolicyAdapter extends RecyclerView.Adapter<PrivacyPolicyViewHolder> {
    private final Context context;
    private final List<PrivacyPolicyData> data;

    public PrivacyPolicyAdapter(Context context, List<PrivacyPolicyData> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public PrivacyPolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_privacy_policy_item,parent,false);
        return new PrivacyPolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrivacyPolicyViewHolder holder, int position) {

        if(data.get(position)!=null){
            PrivacyPolicyData privacyPolicyData =data.get(position);

            if(!TextUtils.isEmpty(privacyPolicyData.getName()))
                holder.tvTitle.setText(privacyPolicyData.getName());
            if(!TextUtils.isEmpty(privacyPolicyData.getDescription()))
                holder.tvDesc.setText(privacyPolicyData.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }
}

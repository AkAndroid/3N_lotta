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
import com.a3nlotta.viewHolder.FAQViewHolder;

import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQViewHolder> {
    private final Context context;
    private final List<PrivacyPolicyData> data;

    public FAQAdapter(Context context, List<PrivacyPolicyData> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_item,parent,false);
        return new FAQViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQViewHolder holder, int position) {
        if(data.get(position)!=null){
            PrivacyPolicyData policyData = data.get(position);
            if(!TextUtils.isEmpty(policyData.getTitle()))
                holder.tvFAQTitle.setText(policyData.getTitle());
            if(!TextUtils.isEmpty(policyData.getDescription()))
                holder.tvDesc.setText(policyData.getDescription());

        }
    }

    @Override
    public int getItemCount() {
        return data!=null?data.size():0;
    }
}

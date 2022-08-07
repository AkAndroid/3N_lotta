package com.a3nlotta.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.a3nlotta.fragment.ContactUsFragment;
import com.a3nlotta.fragment.FAQFragment;
import com.a3nlotta.fragment.FeedbackFragment;

public class HelpViewPagerAdapter extends FragmentStateAdapter {
    private static final int CARD_ITEM_SIZE = 2;
    public HelpViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override public Fragment createFragment(int position) {
        switch (position){
            //case 0: return FeedbackFragment.newInstance();
            case 0: return FAQFragment.newInstance();
            case 1: return ContactUsFragment.newInstance();
        }

        return FeedbackFragment.newInstance();
    }
    @Override public int getItemCount() {
        return CARD_ITEM_SIZE;
    }
}
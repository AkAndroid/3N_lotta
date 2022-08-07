package com.a3nlotta.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.a3nlotta.fragment.ContactUsFragment;
import com.a3nlotta.fragment.FAQFragment;
import com.a3nlotta.fragment.FeedbackFragment;
import com.a3nlotta.fragment.MyPlaysFragment;

public class MyPlayViewPagerAdapter extends FragmentStateAdapter {
    private static final int CARD_ITEM_SIZE = 2;
    public MyPlayViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override public Fragment createFragment(int position) {
        switch (position){
            //case 0: return FeedbackFragment.newInstance();
            case 0: return MyPlaysFragment.newInstance("week_draw");
            case 1: return MyPlaysFragment.newInstance("month_draw");
        }

        return FeedbackFragment.newInstance();
    }
    @Override public int getItemCount() {
        return CARD_ITEM_SIZE;
    }
}
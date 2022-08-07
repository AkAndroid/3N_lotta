package com.a3nlotta.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.a3nlotta.fragment.AllWinnerFragment;

public class WinnerViewPagerAdapter extends FragmentStateAdapter {
    private static final int CARD_ITEM_SIZE = 2;
    public WinnerViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override public Fragment createFragment(int position) {
        return AllWinnerFragment.newInstance();
    }
    @Override public int getItemCount() {
        return CARD_ITEM_SIZE;
    }
}
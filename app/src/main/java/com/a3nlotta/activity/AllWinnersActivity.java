package com.a3nlotta.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.a3nlotta.R;
import com.a3nlotta.adapter.WinnerViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllWinnersActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager2 viewPager;

    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_winners);
        ButterKnife.bind(this);

        viewPager.setAdapter(createCardAdapter());
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        switch (position){
                            case 0:tab.setText("Weekly");
                                    break;
                            case 1:tab.setText("Monthly");
                                break;
                        }
                    }
                }).attach();
    }

    private WinnerViewPagerAdapter createCardAdapter() {
        return new WinnerViewPagerAdapter(this);
    }
}
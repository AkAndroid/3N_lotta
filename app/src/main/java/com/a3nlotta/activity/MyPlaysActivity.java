package com.a3nlotta.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.a3nlotta.R;
import com.a3nlotta.adapter.HelpViewPagerAdapter;
import com.a3nlotta.adapter.MyPalysAdapter;
import com.a3nlotta.adapter.MyPlayViewPagerAdapter;
import com.a3nlotta.model.home.HomeModel;
import com.a3nlotta.model.mayplay.MyPlaysModel;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class MyPlaysActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_my_plays);
        ButterKnife.bind(this);

        viewPager.setAdapter(new MyPlayViewPagerAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        switch (position){
                            /*case 0:tab.setText("Feedback");
                                break;*/
                            case 0:tab.setText("Weekly");
                                break;
                            case 1:tab.setText("Monthly");
                                break;
                        }
                    }
                }).attach();

    }


}
package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a3nlotta.model.ProfileModel;
import com.a3nlotta.model.home.HomeModel;
import com.a3nlotta.model.home.WinnerModel;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.SharedPreferencesManager;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.a3nlotta.R;
import com.a3nlotta.adapter.DrawsAdapter;
import com.a3nlotta.customeView.carouselview.CarouselView;
import com.a3nlotta.customeView.carouselview.ViewListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.ivWinner1)
    ImageView ivWinner1;
    @BindView(R.id.tvWinner1Name)
    TextView tvWinner1Name;
    @BindView(R.id.tvWinner1Amount)
    TextView tvWinner1Amount;

    @BindView(R.id.ivWinner2)
    ImageView ivWinner2;
    @BindView(R.id.tvWinner2Name)
    TextView tvWinner2Name;
    @BindView(R.id.tvWinner2Amount)
    TextView tvWinner2Amount;

    @BindView(R.id.ivWinner3)
    ImageView ivWinner3;
    @BindView(R.id.tvWinner3Name)
    TextView tvWinner3Name;
    @BindView(R.id.tvWinner3Amount)
    TextView tvWinner3Amount;

    @BindView(R.id.carousel)
    CarouselView carouselView;
    @BindView(R.id.tvWalletBal)
    TextView tvWalletBal;
    @BindView(R.id.tvCurrency)
    TextView tvCurrency;

    @BindView(R.id.ivMenu)
    ImageView ivMenu;
    private ImageView ivUser;
    private TextView tvUserName;
    private TextView tvUserWallet;
    private HomeModel homeModel;

    @OnClick(R.id.ivMenu)
    public void onLogoClick(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            drawer.openDrawer(GravityCompat.START);
        }
    }
    @BindView(R.id.rvDraws)
    RecyclerView rvDraws;

    DrawerLayout drawer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        ivUser=navigationView.getHeaderView(0).findViewById(R.id.ivUser);
        tvUserName=navigationView.getHeaderView(0).findViewById(R.id.tvUserName);
        tvUserWallet=navigationView.getHeaderView(0).findViewById(R.id.tvUserWallet);


        tvCurrency.setText(SharedPreferencesManager.getCurrency());
        navigationView.setNavigationItemSelectedListener(this);

        if (SharedPreferencesManager.isLogin()) {
            navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LandingActivity.this,ProfileActivity.class));
                }
            });

            Glide.with(LandingActivity.this).load(SharedPreferencesManager.getImgUrl()).error(R.drawable.ic_avtar).into(ivUser);
            tvUserName.setText(SharedPreferencesManager.getName());

            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
        }else{
            //navigationView.getHeaderView(0).setVisibility(View.GONE);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        WebAPI.sendRegistrationToServer(LandingActivity.this,token);
                    }
                });

        getHomeData();
        if (SharedPreferencesManager.isLogin())
            getProfile();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(LandingActivity.this).load(SharedPreferencesManager.getImgUrl()).error(R.drawable.ic_avtar).into(ivUser);

        setUserData();
        tvCurrency.setText(SharedPreferencesManager.getCurrency());
        if(homeModel!=null)
            setData(homeModel);
    }

    private void getHomeData() {
        try{
            JSONObject jsonObject = new JSONObject();

            AlertDialog dialog = Utilities.getProgressDialog(LandingActivity.this, getString(R.string.loading));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.HOME, Request.Method.GET, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            homeModel = new Gson().fromJson(response.toString(),HomeModel.class);
                            setData(homeModel);
                        }else{
                            Toasty.error(LandingActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(LandingActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getProfile() {
        try{
            JSONObject jsonObject = new JSONObject();


            WebAPI.call(this, jsonObject, Constants.GET_USER_DATA, Request.Method.GET, new VolleyManager() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getBoolean("success")){
                            JSONArray array = response.getJSONArray("data");
                            JSONObject profile = array.getJSONObject(0);

                            SharedPreferencesManager.setProfile(profile.toString());

                            setUserData();



                        }else{
                            Toasty.error(LandingActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toasty.error(LandingActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUserData() {
        ProfileModel profileModel = SharedPreferencesManager.getProfile();

        if(profileModel!=null){
            if(!TextUtils.isEmpty(profileModel.getProfileImg()))
                Glide.with(LandingActivity.this).load(profileModel.getProfileImg()).error(R.drawable.ic_avtar).into(ivUser);

            if(!TextUtils.isEmpty(profileModel.getName()))
                tvUserName.setText(profileModel.getName());
            if(!TextUtils.isEmpty(profileModel.getWallet())) {
                tvUserWallet.setText(profileModel.getWallet() + SharedPreferencesManager.getCurrency());
                tvWalletBal.setText(profileModel.getWallet());
            }
        }
    }

    private void setData(HomeModel homeModel) {
        if(homeModel !=null){
            if(homeModel.getBanners()!=null && homeModel.getBanners().size()>0){
                carouselView.setViewListener(new ViewListener() {

                    @Override
                    public View setViewForPosition(int position) {
                        View customView  = LayoutInflater.from(LandingActivity.this).inflate(R.layout.slider_item_view, null);
                        ImageView imageView = customView.findViewById(R.id.imageView);
                        Glide.with(LandingActivity.this).load(homeModel.getBanners().get(position).getImage()).into(imageView);
                        return customView;
                    }
                });
                carouselView.setPageCount(homeModel.getBanners().size());

            }
            if(homeModel.getDraw()!=null && homeModel.getDraw().size()>0){
                rvDraws.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
                rvDraws.setAdapter(new DrawsAdapter(this,homeModel.getDraw()));
            }


            if(homeModel.getBanners()!=null && homeModel.getWinners().size()>0){
                for(WinnerModel winnerModel : homeModel.getWinners()){
                    switch (winnerModel.getId()){
                        case 1:
                            Glide.with(LandingActivity.this).load(winnerModel.getImage()).error(R.drawable.ic_avtar).into(ivWinner1);
                            if(!TextUtils.isEmpty(winnerModel.getName()))
                                tvWinner1Name.setText(winnerModel.getName());
                            if(!TextUtils.isEmpty(winnerModel.getWinPrice()))
                                tvWinner1Amount.setText("Win - "+winnerModel.getWinPrice()+SharedPreferencesManager.getCurrency());
                            break;
                        case 2:
                            Glide.with(LandingActivity.this).load(winnerModel.getImage()).error(R.drawable.ic_avtar).into(ivWinner2);
                            if(!TextUtils.isEmpty(winnerModel.getName()))
                                tvWinner2Name.setText(winnerModel.getName());
                            if(!TextUtils.isEmpty(winnerModel.getWinPrice()))
                                tvWinner2Amount.setText("Win - "+winnerModel.getWinPrice()+SharedPreferencesManager.getCurrency());
                            break;
                        case 3:
                            Glide.with(LandingActivity.this).load(winnerModel.getImage()).error(R.drawable.ic_avtar).into(ivWinner3);
                            if(!TextUtils.isEmpty(winnerModel.getName()))
                                tvWinner3Name.setText(winnerModel.getName());
                            if(!TextUtils.isEmpty(winnerModel.getWinPrice()))
                                tvWinner3Amount.setText("Win - "+winnerModel.getWinPrice()+SharedPreferencesManager.getCurrency());
                            break;

                    }
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {

        }else if (id == R.id.nav_wallet) {
            if (SharedPreferencesManager.isLogin())
                startActivity(new Intent(this,MyWalletActivity.class));
            else
                login();
        }else if (id == R.id.nav_feedback) {
            startActivity(new Intent(this,FeedbackActivity.class));
        }else if (id == R.id.nav_play) {
            if (SharedPreferencesManager.isLogin())
                startActivity(new Intent(this,MyPlaysActivity.class));
            else
                login();
        }else if (id == R.id.nav_help) {
            startActivity(new Intent(this, HelpActivity.class));
        }else if (id == R.id.nav_refer) {
            if (SharedPreferencesManager.isLogin())
                startActivity(new Intent(this,ReferEarnActivity.class));
            else
                login();
        }else if (id == R.id.nav_winners) {
            startActivity(new Intent(this,AllWinnersActivity.class));
        }else if (id == R.id.nav_logout) {
            logOut();
        }else if (id == R.id.nav_login) {
            login();
        }else if (id == R.id.nav_terms) {
            Intent i = new Intent(this, PrivacyPolicyActivity.class);
            i.putExtra("title",getString(R.string.terms_and_rules));
            startActivity(i);
        }else if (id == R.id.nav_privacy_policy) {
            Intent i = new Intent(this, PrivacyPolicyActivity.class);
            i.putExtra("title",getString(R.string.privacy_policy));
            startActivity(i);
        }else if (id == R.id.nav_setting) {
            if (SharedPreferencesManager.isLogin())
                startActivity(new Intent(this,SettingActivity.class));
            else
                login();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void login() {
        startActivity(new Intent(this,LoginActivity.class));
    }

    private void logOut() {
        androidx.appcompat.app.AlertDialog.Builder sayWindows = new androidx.appcompat.app.AlertDialog.Builder(
                this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.logout_alert_dialog_layout, null);
        sayWindows.setView(dialogView);

        androidx.appcompat.app.AlertDialog mAlertDialog = sayWindows.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mAlertDialog.show();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.setCancelable(false);

        TextView tvMsg = (TextView) dialogView.findViewById(R.id.tvMsg);

        Button btPositive = (Button) dialogView.findViewById(R.id.btPositive);
        Button btNegative = (Button) dialogView.findViewById(R.id.btNegative);


        btPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManager.clearPreference();

                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

                mAlertDialog.dismiss();
            }
        });
        btNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });

        mAlertDialog.show();
    }
}
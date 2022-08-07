package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.a3nlotta.R;
import com.a3nlotta.utils.SharedPreferencesManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /*if (SharedPreferencesManager.isLogin()) {*/
                    Intent i = new Intent(SplashScreen.this, LandingActivity.class);
                    startActivity(i);
                    finish();

                /*} else   if (!SharedPreferencesManager.isLogin()) {
                    Intent i2 = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i2);
                    finish();
                }*/

            }
        }, 2000);

    }
}
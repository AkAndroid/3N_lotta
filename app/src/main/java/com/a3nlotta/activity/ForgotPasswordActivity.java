package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.a3nlotta.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends AppCompatActivity {
    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
    }
}
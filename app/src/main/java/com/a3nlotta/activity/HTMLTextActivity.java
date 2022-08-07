package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.a3nlotta.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HTMLTextActivity extends AppCompatActivity {

    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvMsg)
    TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_text);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            if (bundle.containsKey("title")) {
                tvTitle.setText(bundle.getString("title"));
            }
            if (bundle.containsKey("msg")) {
                tvMsg.setText(Html.fromHtml(bundle.getString("msg"), Html.FROM_HTML_MODE_COMPACT));
            }
        }
    }
}
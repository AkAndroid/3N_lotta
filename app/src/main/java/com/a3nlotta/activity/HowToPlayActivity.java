package com.a3nlotta.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a3nlotta.R;
import com.a3nlotta.adapter.HowToPlayAndNoteAdapter;
import com.a3nlotta.adapter.MyPalysAdapter;
import com.a3nlotta.model.draw.HowToPlayModel;
import com.a3nlotta.model.mayplay.MyPlaysModel;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class HowToPlayActivity extends AppCompatActivity {

    private String drawName="";

    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }
    
    @BindView(R.id.tvDrawName)
    TextView tvDrawName;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rvHowToPlay)
    RecyclerView rvHowToPlay;
    @BindView(R.id.rvNote)
    RecyclerView rvNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            if (bundle.containsKey("draw_name")) {
                drawName=bundle.getString("draw_name");
                if(drawName.toLowerCase().contains("month")){
                    tvTitle.setText(getString(R.string.how_to_win));
                    tvDrawName.setText(getString(R.string.how_to_win));
                }
                getHowToPlayData();
            }
        }
    }

    private void getHowToPlayData() {
        try{
            JSONObject jsonObject = new JSONObject();
            if(drawName.toLowerCase().contains("month"))
                jsonObject.put("type","month_draw");
            else
                jsonObject.put("type","week_draw");

            AlertDialog dialog = Utilities.getProgressDialog(HowToPlayActivity.this, getString(R.string.loading));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.GET_PLAY_DATA, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            HowToPlayModel howToPlayModel = new Gson().fromJson(response.toString(),HowToPlayModel.class);
                            if(howToPlayModel!=null && howToPlayModel.getData()!=null){
                                rvHowToPlay.setLayoutManager(new LinearLayoutManager(HowToPlayActivity.this, RecyclerView.VERTICAL,false));
                                rvHowToPlay.setAdapter(new HowToPlayAndNoteAdapter(HowToPlayActivity.this,howToPlayModel.getData().getHowToPlay()));

                                rvNote.setLayoutManager(new LinearLayoutManager(HowToPlayActivity.this, RecyclerView.VERTICAL,false));
                                rvNote.setAdapter(new HowToPlayAndNoteAdapter(HowToPlayActivity.this,howToPlayModel.getData().getNote()));
                            }

                        }else{
                            Toasty.error(HowToPlayActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(HowToPlayActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Switch;
import android.widget.Toast;

import com.a3nlotta.R;
import com.a3nlotta.customeView.SwitchButton;
import com.a3nlotta.model.ProfileModel;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.SharedPreferencesManager;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SettingActivity extends AppCompatActivity {

    
    @BindView(R.id.switchButtonSounds)
    SwitchButton switchButtonSounds;

    @BindView(R.id.switchButtonVibration)
    SwitchButton switchButtonVibration;

    @BindView(R.id.switchButtonPush)
    SwitchButton switchButtonPush;

    @BindView(R.id.switchButtonEmail)
    SwitchButton switchButtonEmail;
    
    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        ProfileModel profileModel = SharedPreferencesManager.getProfile();

        if(profileModel!=null){
            if(!TextUtils.isEmpty(profileModel.getVibration()))
                switchButtonVibration.setChecked(Integer.parseInt(profileModel.getVibration())==1);
            if(!TextUtils.isEmpty(profileModel.getSounds()))
                switchButtonSounds.setChecked(Integer.parseInt(profileModel.getSounds())==1);
            if(!TextUtils.isEmpty(profileModel.getPush()))
                switchButtonPush.setChecked(Integer.parseInt(profileModel.getPush())==1);
            if(!TextUtils.isEmpty(profileModel.getEmailNotifications()))
                switchButtonEmail.setChecked(Integer.parseInt(profileModel.getEmailNotifications())==1);

        }


        switchButtonSounds.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                sendSetting("sounds");
            }
        });

        switchButtonVibration.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                sendSetting("vibration");
            }
        });

        switchButtonPush.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                sendSetting("push");
            }
        });

        switchButtonEmail.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                sendSetting("email_notifications");
            }
        });
    }
    
    public void sendSetting(String type){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type",type);

            switch(type){
                case "lang":
                    jsonObject.put("lang","en");
                    break;
                case "sounds":
                    jsonObject.put("sounds",switchButtonSounds.isChecked()?1:0);
                    break;
                case "vibration":
                    jsonObject.put("vibration",switchButtonVibration.isChecked()?1:0);
                    break;
                case "push":
                    jsonObject.put("push",switchButtonPush.isChecked()?1:0);
                    break;
                case "email_notifications":
                    jsonObject.put("email_notifications",switchButtonEmail.isChecked()?1:0);
                    break;
            }

            AlertDialog dialog = Utilities.getProgressDialog(SettingActivity.this, getString(R.string.request_submiting));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.SETTINGS, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            Toasty.success(SettingActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();

                            ProfileModel profileModel = SharedPreferencesManager.getProfile();
                            if(profileModel!=null){
                                switch(type){
                                    case "lang":
                                        profileModel.setLang("en");
                                        break;
                                    case "sounds":
                                        profileModel.setSounds(switchButtonSounds.isChecked()?"1":"0");
                                        break;
                                    case "vibration":
                                        profileModel.setVibration(switchButtonVibration.isChecked()?"1":"0");
                                        break;
                                    case "push":
                                        profileModel.setPush(switchButtonPush.isChecked()?"1":"0");
                                        break;
                                    case "email_notifications":
                                        profileModel.setEmailNotifications(switchButtonEmail.isChecked()?"1":"0");
                                        break;
                                }

                                SharedPreferencesManager.setProfile(new Gson().toJson(profileModel));
                            }

                        }else{
                            Toasty.error(SettingActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(SettingActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
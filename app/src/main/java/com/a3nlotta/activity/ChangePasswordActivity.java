package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.a3nlotta.R;
import com.a3nlotta.adapter.MyPalysAdapter;
import com.a3nlotta.model.mayplay.MyPlaysModel;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.SharedPreferencesManager;
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

public class ChangePasswordActivity extends AppCompatActivity {
    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }

    @OnClick(R.id.btSave)
    public void onSaveClick(){
        if(TextUtils.isEmpty(etOldPass.getText()))
            Toasty.error(this,getString(R.string.enter_current_password)).show();
        else if(TextUtils.isEmpty(etNewPass.getText()))
            Toasty.error(this,getString(R.string.enter_new_password)).show();
        else if(TextUtils.isEmpty(etConfPass.getText()))
            Toasty.error(this,getString(R.string.enter_confirm_password)).show();
        else if(!etConfPass.getText().toString().equals(etNewPass.getText().toString()))
            Toasty.error(this,getString(R.string.password_not_matching)).show();
        else
            changePassword();
    }

    private void changePassword() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", SharedPreferencesManager.getEmail());
            jsonObject.put("oldPassword",etOldPass.getText().toString());
            jsonObject.put("newPassword",etNewPass.getText().toString());
            AlertDialog dialog = Utilities.getProgressDialog(ChangePasswordActivity.this, getString(R.string.saving));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.CHANGE_PASSWORD, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            Toasty.success(ChangePasswordActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }else{
                            Toasty.error(ChangePasswordActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(ChangePasswordActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @BindView(R.id.etOldPass)
    EditText etOldPass;

    @BindView(R.id.etNewPass)
    EditText etNewPass;

    @BindView(R.id.etConfPass)
    EditText etConfPass;

    boolean oldPassVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);

        etOldPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etOldPass.getRight() - etOldPass.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(oldPassVisible){
                            etOldPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            etOldPass.setCompoundDrawables(null,null,getResources().getDrawable(R.drawable.ic_visibility,null),null);
                        }else{
                            etOldPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            etOldPass.setCompoundDrawables(null,null,getResources().getDrawable(R.drawable.ic_visibility_off,null),null);
                        }
                       return true;
                    }
                }
                return false;
            }
        });
    }
}
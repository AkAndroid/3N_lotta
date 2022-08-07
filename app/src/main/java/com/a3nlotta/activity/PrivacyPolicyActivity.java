package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import com.a3nlotta.R;
import com.a3nlotta.adapter.PaymentHistoryAdapter;
import com.a3nlotta.adapter.PrivacyPolicyAdapter;
import com.a3nlotta.model.privacypolicy.PrivacyPolicyModel;
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

public class PrivacyPolicyActivity extends AppCompatActivity {
    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rvPrivacyPolicy)
    RecyclerView rvPrivacyPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            if (bundle.containsKey("title")) {
                tvTitle.setText(bundle.getString("title"));

                if(bundle.getString("title").equals(getString(R.string.terms_and_rules))){
                    getTermsAndRules();
                }else if(bundle.getString("title").equals(getString(R.string.privacy_policy))){
                    getPrivacyPolicy();
                }
            }

        }
    }

    private void getPrivacyPolicy() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lang","en");
            AlertDialog dialog = Utilities.getProgressDialog(PrivacyPolicyActivity.this, getString(R.string.loading));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.PRIVACY_POLICY, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            PrivacyPolicyModel privacyPolicyModel = new Gson().fromJson(response.toString(),PrivacyPolicyModel.class);
                            if(privacyPolicyModel!=null && privacyPolicyModel.getData()!=null){
                                rvPrivacyPolicy.setLayoutManager(new LinearLayoutManager(PrivacyPolicyActivity.this,RecyclerView.VERTICAL,false));
                                rvPrivacyPolicy.setAdapter(new PrivacyPolicyAdapter(PrivacyPolicyActivity.this,privacyPolicyModel.getData()));
                            }
                        }else{
                            Toasty.error(PrivacyPolicyActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(PrivacyPolicyActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getTermsAndRules() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lang","en");
            AlertDialog dialog = Utilities.getProgressDialog(PrivacyPolicyActivity.this, getString(R.string.loading));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.TERMS_AND_COND, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            PrivacyPolicyModel privacyPolicyModel = new Gson().fromJson(response.toString(),PrivacyPolicyModel.class);
                            if(privacyPolicyModel!=null && privacyPolicyModel.getData()!=null){
                                rvPrivacyPolicy.setLayoutManager(new LinearLayoutManager(PrivacyPolicyActivity.this,RecyclerView.VERTICAL,false));
                                rvPrivacyPolicy.setAdapter(new PrivacyPolicyAdapter(PrivacyPolicyActivity.this,privacyPolicyModel.getData()));
                            }
                        }else{
                            Toasty.error(PrivacyPolicyActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(PrivacyPolicyActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
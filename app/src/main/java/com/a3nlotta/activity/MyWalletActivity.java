package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.a3nlotta.R;
import com.a3nlotta.model.ProfileModel;
import com.a3nlotta.model.home.HomeModel;
import com.a3nlotta.model.wallet.WalletModel;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.SharedPreferencesManager;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class MyWalletActivity extends AppCompatActivity {
    private WalletModel walletModel;
    double walletAmount=0;
    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }

    @OnClick(R.id.btWithdrawn)
    public void onWithdrawnClick(){
        if(TextUtils.isEmpty(etBankName.getText()))
            Toasty.error(this,getString(R.string.enter_bank_name)).show();
        else if(TextUtils.isEmpty(etAccountName.getText()))
            Toasty.error(this,getString(R.string.enter_name_in_account)).show();
        else if(TextUtils.isEmpty(etAccountNo.getText()))
            Toasty.error(this,getString(R.string.enter_account_no)).show();
        else if(TextUtils.isEmpty(etBranchCode.getText()))
            Toasty.error(this,getString(R.string.enter_bin_swift_code_of_bank)).show();
        else if(TextUtils.isEmpty(etAmount.getText()))
            Toasty.error(this,getString(R.string.enter_amount)).show();
        else if(Double.parseDouble(etAmount.getText().toString())>walletAmount)
            Toasty.error(this,getString(R.string.enter_valid_amount)).show();
        else if(TextUtils.isEmpty(etMobileNo.getText()))
            Toasty.error(this,getString(R.string.enter_mobile)).show();
        else{
            try{
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("bank_name",etBankName.getText());
                jsonObject.put("account_name",etAccountName.getText());
                jsonObject.put("account_number",etAccountNo.getText());
                jsonObject.put("swift_code",etBranchCode.getText());
                jsonObject.put("amount",etAmount.getText());
                jsonObject.put("contact",etMobileNo.getText());

                AlertDialog dialog = Utilities.getProgressDialog(this, getString(R.string.request_submiting));
                dialog.show();
                WebAPI.call(this, jsonObject, Constants.REQUEST_MONEY, Request.Method.POST, new VolleyManager() {

                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.cancel();
                        try {
                            if(response.getBoolean("success")){

                                //Toasty.success(getContext(), response.getString("msg"), Toast.LENGTH_LONG).show();
                                etBankName.setText("");
                                etAccountName.setText("");
                                etAccountNo.setText("");
                                etBranchCode.setText("");
                                etAmount.setText("");
                                etMobileNo.setText("");
                                Utilities.showCustomAlert(MyWalletActivity.this,getString(R.string.money_will_reach_to_you_soon),false,true,null);
                                getWalletData();
                            }else{
                                Toasty.error(MyWalletActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.cancel();
                        Toasty.error(MyWalletActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @OnClick(R.id.tvHistory)
    public void onHistoryClick(){
        startActivity(new Intent(this,PaymentHistoryActivity.class));
    }

    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.tvWithdraw)
    TextView tvWithdraw;

    @BindView(R.id.etBankName)
    TextInputEditText etBankName;
    @BindView(R.id.etAccountName)
    TextInputEditText etAccountName;
    @BindView(R.id.etAccountNo)
    TextInputEditText etAccountNo;
    /*@BindView(R.id.etConfAccountNo)
    TextInputEditText etConfAccountNo;*/
    @BindView(R.id.etBranchCode)
    TextInputEditText etBranchCode;
    @BindView(R.id.etAmount)
    TextInputEditText etAmount;
    @BindView(R.id.etMobileNo)
    TextInputEditText etMobileNo;

    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserLocation)
    TextView tvUserLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        ButterKnife.bind(this);


        ProfileModel profileModel = SharedPreferencesManager.getProfile();
        if(profileModel!=null){
            if(!TextUtils.isEmpty(profileModel.getProfileImg()))
                Glide.with(this).load(profileModel.getProfileImg()).error(R.drawable.ic_avtar).into(circleImageView);

            if(!TextUtils.isEmpty(profileModel.getName()))
                tvUserName.setText(profileModel.getName());
            if(!TextUtils.isEmpty(profileModel.getLastName()))
                tvUserName.setText(tvUserName.getText()+" "+profileModel.getLastName());

            if(!TextUtils.isEmpty(profileModel.getCountry()))
                tvUserLocation.setText(profileModel.getCountry());


        }

        getWalletData();
    }

    private void getWalletData() {
        try{
            JSONObject jsonObject = new JSONObject();

            AlertDialog dialog = Utilities.getProgressDialog(MyWalletActivity.this, getString(R.string.loading));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.MY_WALLET, Request.Method.GET, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            walletModel = new Gson().fromJson(response.toString(),WalletModel.class);
                            if(walletModel!=null){
                                if(walletModel.getWalletBalance()!=null && walletModel.getWalletBalance().size()>0) {
                                    tvBalance.setText(walletModel.getWalletBalance().get(0).getWallet() + SharedPreferencesManager.getCurrency());
                                    walletAmount =Double.parseDouble(walletModel.getWalletBalance().get(0).getWallet());
                                }else{
                                    tvBalance.setText("0"+SharedPreferencesManager.getCurrency());
                                }
                                if(walletModel.getLastWithdraw()!=null && walletModel.getLastWithdraw().size()>0)
                                    tvWithdraw.setText(walletModel.getLastWithdraw().get(0).getWithdrawAmount()+SharedPreferencesManager.getCurrency());
                                else
                                    tvWithdraw.setText("0"+SharedPreferencesManager.getCurrency());
                            }

                        }else{
                            Toasty.error(MyWalletActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(MyWalletActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
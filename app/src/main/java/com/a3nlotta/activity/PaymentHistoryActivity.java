package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.a3nlotta.R;
import com.a3nlotta.adapter.MyPalysAdapter;
import com.a3nlotta.adapter.PaymentHistoryAdapter;
import com.a3nlotta.model.wallet.WalletModel;
import com.a3nlotta.model.wallet.WithdrawHistoryModel;
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

public class PaymentHistoryActivity extends AppCompatActivity {

    @BindView(R.id.rvPaymentHistory)
    RecyclerView rvPaymentHistory;
    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        ButterKnife.bind(this);

        getHistoryData();


    }

    private void getHistoryData() {
        try{
            JSONObject jsonObject = new JSONObject();

            AlertDialog dialog = Utilities.getProgressDialog(this, getString(R.string.loading));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.MY_WALLET, Request.Method.GET, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            WithdrawHistoryModel withdrawHistoryModel = new Gson().fromJson(response.toString(), WithdrawHistoryModel.class);
                            if(withdrawHistoryModel!=null){
                                rvPaymentHistory.setLayoutManager(new LinearLayoutManager(PaymentHistoryActivity.this,RecyclerView.VERTICAL,false));
                                rvPaymentHistory.setAdapter(new PaymentHistoryAdapter(PaymentHistoryActivity.this,withdrawHistoryModel.getData()));
                            }

                        }else{
                            Toasty.error(PaymentHistoryActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(PaymentHistoryActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
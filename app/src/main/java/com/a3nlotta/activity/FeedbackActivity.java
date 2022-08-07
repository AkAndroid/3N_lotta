package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a3nlotta.R;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class FeedbackActivity extends AppCompatActivity {
    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }

    @OnClick(R.id.bt1star)
    public void on1Click(){
        ratingBar.setRating(1);
    }
    @OnClick(R.id.bt2star)
    public void on12Click(){
        ratingBar.setRating(2);
    }
    @OnClick(R.id.bt3star)
    public void on3Click(){
        ratingBar.setRating(3);
    }
    @OnClick(R.id.bt4star)
    public void on4Click(){
        ratingBar.setRating(4);
    }
    @OnClick(R.id.bt5star)
    public void on5Click(){
        ratingBar.setRating(5);
    }
    @OnClick(R.id.btSubmit)
    public void onSubmitClick(){
       if(TextUtils.isEmpty(etReview.getText().toString())||etReview.getText().toString().length()<5){
           Toasty.error(this,R.string.enter_min_5_char_in_review).show();
       }else{
           try{
               JSONObject jsonObject = new JSONObject();
               jsonObject.put("review",etReview.getText());
               jsonObject.put("rating",ratingBar.getRating());

               AlertDialog dialog = Utilities.getProgressDialog(this, getString(R.string.request_submiting));
               dialog.show();
               WebAPI.call(this, jsonObject, Constants.FEEDBACK, Request.Method.POST, new VolleyManager() {

                   @Override
                   public void onResponse(JSONObject response) {
                       dialog.cancel();
                       try {
                           if(response.getBoolean("success")){
                               Toasty.success(FeedbackActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                               etReview.setText("");
                               ratingBar.setRating(5);
                               Utilities.showCustomAlert(FeedbackActivity.this,getString(R.string.thank_you_for_your_feedback),false,true,null);
                               //onBackPressed();
                           }else{
                               Toasty.error(FeedbackActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                   }
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       dialog.cancel();
                       Toasty.error(FeedbackActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                   }
               });
           }catch (Exception e){
               e.printStackTrace();
           }
       }
    }

    @BindView(R.id.etReview)
    EditText etReview;
    @BindView(R.id.tvRemaining)
    TextView tvRemaining;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        etReview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvRemaining.setText((400-count)+" "+getString(R.string.characters_remaining));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.a3nlotta.R;
import com.a3nlotta.listener.DateSelectListener;
import com.a3nlotta.listener.GetCityListener;
import com.a3nlotta.listener.GetCountryListener;
import com.a3nlotta.listener.GetStateListener;
import com.a3nlotta.model.address.CityResponse;
import com.a3nlotta.model.address.CountryResponse;
import com.a3nlotta.model.address.StateResponse;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.SharedPreferencesManager;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    CountryResponse countryResponse;
    CityResponse cityResponse;
    StateResponse stateResponse;

    @BindView(R.id.tvSignIn)
    TextView tvSignIn;

    @BindView(R.id.tvCountryCode)
    TextView tvCountryCode;

    @BindView(R.id.etFName)
    EditText etFName;
    @BindView(R.id.etLName)
    EditText etLName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etDOB)
    EditText etDOB;

    @OnClick(R.id.etDOB)
    void onDobClick(){
        Utilities.getDob(this, new DateSelectListener() {
            @Override
            public void onDateSelected(String date) {
                etDOB.setText(date);
            }
        });
    }

    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etMobileNo)
    EditText etMobileNo;
    @BindView(R.id.etOTP)
    EditText etOTP;

    @BindView(R.id.spGender)
    Spinner spGender;

    @BindView(R.id.spCountry)
    Spinner spCountry;
    @BindView(R.id.spState)
    Spinner spState;
    @BindView(R.id.spCity)
    Spinner spCity;

    @OnClick(R.id.tvSignIn)
    void onSigInClick(){
        onBackPressed();
    }

    @BindView(R.id.btSignUp)
    Button btSignUp;
    @OnClick(R.id.btSignUp)
    void onSigUpClick(){
        if(btSignUp.getText().toString().equalsIgnoreCase(getString(R.string.send_otp))){
            if(TextUtils.isEmpty(etFName.getText())){
                Toast.makeText(this, R.string.enter_first_name,Toast.LENGTH_LONG).show();
            }else if(TextUtils.isEmpty(etLName.getText())){
                Toast.makeText(this, R.string.enter_last_name,Toast.LENGTH_LONG).show();
            }else if(TextUtils.isEmpty(etEmail.getText())){
                Toast.makeText(this, R.string.enter_email,Toast.LENGTH_LONG).show();
            }else if(spGender.getSelectedItem().toString().equalsIgnoreCase("gender")){
                Toast.makeText(this, R.string.select_gender,Toast.LENGTH_LONG).show();
            }else if(TextUtils.isEmpty(etDOB.getText())){
                Toast.makeText(this, R.string.select_dob,Toast.LENGTH_LONG).show();
            }else if(spCountry.getSelectedItem().toString().equalsIgnoreCase("Country")){
                Toast.makeText(this, R.string.select_country,Toast.LENGTH_LONG).show();
            }else if(spState.getSelectedItem().toString().equalsIgnoreCase("State")){
                Toast.makeText(this, R.string.select_state,Toast.LENGTH_LONG).show();
            }else if(spCity.getSelectedItem().toString().equalsIgnoreCase("City")){
                Toast.makeText(this, R.string.select_city,Toast.LENGTH_LONG).show();
            }else if(TextUtils.isEmpty(etPassword.getText())){
                Toast.makeText(this, R.string.enter_password,Toast.LENGTH_LONG).show();
            }else if(TextUtils.isEmpty(etMobileNo.getText())){
                Toast.makeText(this, R.string.enter_mobile,Toast.LENGTH_LONG).show();
            }else if(etMobileNo.getText().toString().length()<10 && etMobileNo.getText().toString().length()>12){
                Toast.makeText(this, R.string.enter_valid_mobile_number,Toast.LENGTH_LONG).show();
            }else {
                sendOTP();
            }
        }else {
            //sign up
            if(TextUtils.isEmpty(etOTP.getText())){
                Toast.makeText(this, R.string.enter_otp,Toast.LENGTH_LONG).show();
            }else{
                register();
            }
        }
    }

    void getCountry(){
        WebAPI.getCountry(this,new GetCountryListener(){

            @Override
            public void onResponse(CountryResponse response) {
                countryResponse=response;
                if(countryResponse!=null && countryResponse.getData()!=null){
                    setArray(spCountry,countryResponse.getCountryArray(),new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItemText = (String) parent.getItemAtPosition(position);
                            if(position > 0){
                                getState(countryResponse.getSelectedCountry(selectedItemText).getId());
                                tvCountryCode.setText("+"+countryResponse.getSelectedCountry(selectedItemText).getPhonecode().toString());
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
        });
    }

    private void getState(Integer countryID) {
        WebAPI.getState(this,countryID,new GetStateListener(){
            @Override
            public void onResponse(StateResponse response) {
                stateResponse=response;
                if(stateResponse!=null && stateResponse.getData()!=null){
                    setArray(spState,stateResponse.getStateArray(),new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItemText = (String) parent.getItemAtPosition(position);
                            if(position > 0){
                                getCity(stateResponse.getSelectedState(selectedItemText).getId());
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

        });
    }

    private void getCity(Integer stateId) {
        WebAPI.getCity(this,stateId,new GetCityListener(){
            @Override
            public void onResponse(CityResponse response) {
                cityResponse=response;
                if(response!=null && response.getData()!=null){
                    setArray(spCity,cityResponse.getCityArray(),new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

        });
    }

    private void sendOTP() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("contact",etMobileNo.getText());;

            AlertDialog dialog = Utilities.getProgressDialog(SignUpActivity.this, getString(R.string.request_submiting));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.SEND_OTP, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        /*if(response.getBoolean("success")){
                            JSONObject user = response.getJSONObject("data");

                            btSignUp.setText(getString(R.string.sign_up));

                            Toasty.success(SignUpActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();

                        }else{
                            Toasty.error(SignUpActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }*/
                        btSignUp.setText(getString(R.string.sign_up));
                        Toasty.success(SignUpActivity.this, "Otp sent successfully", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    //Toasty.error(SignUpActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                    btSignUp.setText(getString(R.string.sign_up));
                    Toasty.success(SignUpActivity.this, "Otp sent successfully", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            btSignUp.setText(getString(R.string.sign_up));
            Toasty.success(SignUpActivity.this, "Otp sent successfully", Toast.LENGTH_LONG).show();
        }
    }

    private void register() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",etFName.getText());
            jsonObject.put("last_name",etLName.getText());
            jsonObject.put("email",etEmail.getText());
            jsonObject.put("contact",etMobileNo.getText());
            jsonObject.put("password",etPassword.getText());
            jsonObject.put("c_password",etPassword.getText());
            jsonObject.put("country",spCountry.getSelectedItem().toString());
            jsonObject.put("state",spState.getSelectedItem().toString());
            jsonObject.put("city",spCity.getSelectedItem().toString());
            jsonObject.put("gender",spGender.getSelectedItem().toString());
            jsonObject.put("dob",etDOB.getText());

            AlertDialog dialog = Utilities.getProgressDialog(SignUpActivity.this, getString(R.string.creating_user));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.REGISTER, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            Toasty.success(SignUpActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }else{
                            Toasty.error(SignUpActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(SignUpActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        tvSignIn.setText(Html.fromHtml(getString(R.string.already_hav_account_sign_in), Html.FROM_HTML_MODE_COMPACT));

        setArray(spGender,getResources().getStringArray(R.array.gender),new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                if(position > 0){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etDOB.setFocusable(false);
        etDOB.setClickable(true);

        getCountry();

        etMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(btSignUp.getText().toString().equalsIgnoreCase(getString(R.string.sign_up)))
                    btSignUp.setText(getString(R.string.send_otp));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    private void setArray(Spinner spinner, String[] stringArray, AdapterView.OnItemSelectedListener callBack) {
        final List<String> plantsList = new ArrayList<>(Arrays.asList(stringArray));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.black,null));
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(callBack);
    }
}
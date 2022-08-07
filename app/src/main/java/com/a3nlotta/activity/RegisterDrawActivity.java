package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.a3nlotta.R;
import com.a3nlotta.listener.GetCityListener;
import com.a3nlotta.listener.GetCountryListener;
import com.a3nlotta.listener.GetStateListener;
import com.a3nlotta.listener.OnDialogButtonClickListener;
import com.a3nlotta.model.ProfileModel;
import com.a3nlotta.model.address.CityResponse;
import com.a3nlotta.model.address.CountryResponse;
import com.a3nlotta.model.address.StateResponse;
import com.a3nlotta.model.draw.DrawModel;
import com.a3nlotta.model.home.tickets.TicketData;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.SharedPreferencesManager;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class RegisterDrawActivity extends AppCompatActivity {
    private DrawModel draw;
    CountryResponse countryResponse;
    CityResponse cityResponse;
    StateResponse stateResponse;
    private TicketData ticketData;
    private String txRef;
    private ProfileModel profileModel;

    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }

    @OnClick(R.id.tvTermsANdCond)
    public void onTvTermsANdCondClick(){
        Intent i = new Intent(this, PrivacyPolicyActivity.class);
        i.putExtra("title",getString(R.string.terms_and_rules));
        startActivity(i);
    }

    @BindView(R.id.btSubmit)
    Button btSubmit;
    @OnClick(R.id.btSubmit)
    public void onSubmitClick(){
        if(btSubmit.getText().toString().equalsIgnoreCase(getString(R.string.send_otp))){
            if(TextUtils.isEmpty(etFName.getText())){
                Toast.makeText(this, R.string.enter_first_name,Toast.LENGTH_LONG).show();
            }else if(TextUtils.isEmpty(etLName.getText())){
                Toast.makeText(this, R.string.enter_last_name,Toast.LENGTH_LONG).show();
            }else if(spGender.getSelectedItem().toString().equalsIgnoreCase("gender")){
                Toast.makeText(this, R.string.select_gender,Toast.LENGTH_LONG).show();
            }else if(spCountry.getSelectedItem().toString().equalsIgnoreCase("Country")){
                Toast.makeText(this, R.string.select_country,Toast.LENGTH_LONG).show();
            }else if(spState.getSelectedItem().toString().equalsIgnoreCase("State")){
                Toast.makeText(this, R.string.select_country,Toast.LENGTH_LONG).show();
            }else if(spCity.getSelectedItem().toString().equalsIgnoreCase("City")){
                Toast.makeText(this, R.string.select_country,Toast.LENGTH_LONG).show();
            }else if(TextUtils.isEmpty(etMobileNo.getText())){
                Toast.makeText(this, R.string.enter_mobile,Toast.LENGTH_LONG).show();
            }else if(!cbTermsCond.isChecked()){
                Toast.makeText(this, R.string.please_accept_and_agree,Toast.LENGTH_LONG).show();
            }else {
                sendOTP();
            }
        }else {
            if(TextUtils.isEmpty(etOTP.getText())){
                Toast.makeText(this, R.string.enter_otp,Toast.LENGTH_LONG).show();
            }else{

                payment();
            }
        }
    }

    private void payment() {

        double amount=0;
        String narration="";
        if(draw!=null) {
            amount = Double.parseDouble(draw.getEntryFee());
            narration = draw.getDrawName();
        }else if(ticketData!=null) {
            amount = Double.parseDouble(ticketData.getFee());
            narration = ticketData.getTicketName();
        }

        ProfileModel profileModel = SharedPreferencesManager.getProfile();
        txRef = System.currentTimeMillis()+""+profileModel.getId();


        new RaveUiManager(this).setAmount(amount)
                .setCurrency(profileModel.getCurrencyType())
                .setEmail(profileModel.getEmail())
                .setfName(etFName.getText().toString())
                .setlName(etLName.getText().toString())
                .setNarration(narration)
                .setPublicKey("FLWPUBK_TEST-990bae4761410e5ed7d49d0319224ae3-X")
                .setEncryptionKey("FLWSECK_TEST45cc6badca04")
                .setTxRef(txRef)
                .setPhoneNumber(profileModel.getContact(), true)
                .acceptAccountPayments(true)
                .acceptCardPayments(true)
                .acceptMpesaPayments(true)
                .acceptAchPayments(true)
                .acceptGHMobileMoneyPayments(true)
                .acceptUgMobileMoneyPayments(true)
                .acceptZmMobileMoneyPayments(true)
                .acceptRwfMobileMoneyPayments(true)
                .acceptSaBankPayments(true)
                .acceptUkPayments(true)
                .acceptBankTransferPayments(true)
                .acceptUssdPayments(true)
                .acceptBarterPayments(true)
                /*.acceptFrancMobileMoneyPayments(true)*/
                .allowSaveCardFeature(true)
                .onStagingEnv(true)
                /*.setMeta(List<Meta>)*/
                /*.withTheme(styleId)*/
                .isPreAuth(true)
                /*.setSubAccounts(List<SubAccount>)*/
                .shouldDisplayFee(true)
                .showStagingLabel(true)
                .initialize();
    }

    private void registerDraw(JSONObject paymentResponse) {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("first_name",etFName.getText());
            jsonObject.put("last_name",etLName.getText());
            jsonObject.put("mobile",etMobileNo.getText());
            jsonObject.put("country",spCountry.getSelectedItem().toString());
            jsonObject.put("state",spState.getSelectedItem().toString());
            jsonObject.put("city",spCity.getSelectedItem().toString());
            jsonObject.put("gender",spGender.getSelectedItem().toString());
            jsonObject.put("otp",etOTP.getText().toString());
            jsonObject.put("draw_name",draw.getDrawName());
            jsonObject.put("fees",draw.getEntryFee());

            jsonObject.put("payment_response",paymentResponse);

            /*jsonObject.put("card_name","1234");
            jsonObject.put("card_number","1234");
            jsonObject.put("month","1234");
            jsonObject.put("year","1234");
            jsonObject.put("cvv","1234");*/

            AlertDialog dialog = Utilities.getProgressDialog(RegisterDrawActivity.this, getString(R.string.request_submiting));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.REGISTER_DRAW, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){

                            Utilities.showCustomAlert(RegisterDrawActivity.this, getString(R.string.for_registering), true,false, new OnDialogButtonClickListener() {
                                @Override
                                public void onPositiveClick(View v, Object o) {
                                    onBackPressed();
                                }

                                @Override
                                public void onNegativeClick(View v, Object o) {
                                    onBackPressed();
                                }
                            });

                            //Toasty.success(RegisterDrawActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();

                        }else{
                            Toasty.error(RegisterDrawActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(RegisterDrawActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void registerMonthDraw(JSONObject paymentResponse) {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("first_name",etFName.getText());
            jsonObject.put("last_name",etLName.getText());
            jsonObject.put("mobile",etMobileNo.getText());
            jsonObject.put("country",spCountry.getSelectedItem().toString());
            jsonObject.put("state",spState.getSelectedItem().toString());
            jsonObject.put("city",spCity.getSelectedItem().toString());
            jsonObject.put("gender",spGender.getSelectedItem().toString());
            jsonObject.put("otp",etOTP.getText().toString());
            jsonObject.put("draw_name",ticketData.getTicketName());
            jsonObject.put("fees",ticketData.getFee());
            jsonObject.put("ticket_id",ticketData.getId());

            jsonObject.put("payment_response",paymentResponse);

            /*jsonObject.put("card_name","1234");
            jsonObject.put("card_number","1234");
            jsonObject.put("month","1234");
            jsonObject.put("year","1234");
            jsonObject.put("cvv","1234");*/

            AlertDialog dialog = Utilities.getProgressDialog(RegisterDrawActivity.this, getString(R.string.request_submiting));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.REGISTER_MONTH_DRAW, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            Toasty.success(RegisterDrawActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                            onBackPressed();
                        }else{
                            Toasty.error(RegisterDrawActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(RegisterDrawActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @BindView(R.id.etFName)
    EditText etFName;
    @BindView(R.id.etLName)
    EditText etLName;
    @BindView(R.id.etOTP)
    EditText etOTP;
    @BindView(R.id.etMobileNo)
    EditText etMobileNo;
    @BindView(R.id.spGender)
    Spinner spGender;

    @BindView(R.id.spCountry)
    Spinner spCountry;
    @BindView(R.id.spState)
    Spinner spState;
    @BindView(R.id.spCity)
    Spinner spCity;
    @BindView(R.id.cbTermsCond)
    CheckBox cbTermsCond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_draw);
        ButterKnife.bind(this);

        if(getIntent().hasExtra("draw")){
            draw = (DrawModel) getIntent().getSerializableExtra("draw");
        }
        if(getIntent().hasExtra("ticket")){
            ticketData = (TicketData) getIntent().getSerializableExtra("ticket");
        }

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

        setProfileData();

        getCountry();

        etMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(btSubmit.getText().toString().equalsIgnoreCase(getString(R.string.sign_up)))
                    btSubmit.setText(getString(R.string.send_otp));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setProfileData() {
        profileModel = SharedPreferencesManager.getProfile();
        if(profileModel!=null){
            if(!TextUtils.isEmpty(profileModel.getName()))
                etFName.setText(profileModel.getName());
            if(!TextUtils.isEmpty(profileModel.getLastName()))
                etLName.setText(profileModel.getLastName());
            if(!TextUtils.isEmpty(profileModel.getContact())) {
                etMobileNo.setText(profileModel.getContact());
                etMobileNo.setEnabled(false);
            }else
                etMobileNo.setEnabled(true);


            if(!TextUtils.isEmpty(profileModel.getGender())) {
                if(profileModel.getGender().equalsIgnoreCase("male"))
                    spGender.setSelection(1);
                else if(profileModel.getGender().equalsIgnoreCase("female"))
                    spGender.setSelection(2);
                else if(profileModel.getGender().equalsIgnoreCase("other"))
                    spGender.setSelection(3);

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
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    if(profileModel!=null && !TextUtils.isEmpty(profileModel.getCountry())) {
                        int countryPos=countryResponse.getSelectedCountryPos(profileModel.getCountry());
                        if(countryPos!=0)
                            spCountry.setSelection( countryPos+ 1);
                    }
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

                    if(profileModel!=null && !TextUtils.isEmpty(profileModel.getState())) {
                        int statePos=stateResponse.getSelectedStatePos(profileModel.getState());
                        if(statePos!=0)
                            spState.setSelection( statePos+ 1);
                    }
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

                    if(profileModel!=null && !TextUtils.isEmpty(profileModel.getCity())) {
                        int cityPos=cityResponse.getSelectedCityPos(profileModel.getCity());
                        if(cityPos!=0)
                            spCity.setSelection(cityPos + 1);
                    }
                }
            }

        });
    }

    private void sendOTP() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("contact",etMobileNo.getText());;

            AlertDialog dialog = Utilities.getProgressDialog(RegisterDrawActivity.this, getString(R.string.request_submiting));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.SEND_OTP, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        /*if(response.getBoolean("success")){
                            btSubmit.setText(getString(R.string.submit));
                            Toasty.success(RegisterDrawActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();

                        }else{
                            Toasty.error(RegisterDrawActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }*/
                        btSubmit.setText(getString(R.string.submit));
                        Toasty.success(RegisterDrawActivity.this, "Otp sent successfully", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    btSubmit.setText(getString(R.string.submit));
                    Toasty.success(RegisterDrawActivity.this, "Otp sent successfully", Toast.LENGTH_LONG).show();

                    //Toasty.error(RegisterDrawActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            btSubmit.setText(getString(R.string.submit));
            Toasty.success(RegisterDrawActivity.this, "Otp sent successfully", Toast.LENGTH_LONG).show();

        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
         *  We advise you to do a further verification of transaction's details on your server to be
         *  sure everything checks out before providing service or goods.
         */
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                /*Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_SHORT).show();*/

                try{
                    JSONObject paymentResponse = new JSONObject(message);
                    SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("SUCCESS")
                            .setContentText("Payment successful \n\n Your Order reference No is :- "+paymentResponse.getJSONObject("data").getString("orderRef"))
                            .showCancelButton(false)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    if(draw!=null)
                                        registerDraw(paymentResponse);
                                    else if(ticketData!=null){
                                        registerMonthDraw(paymentResponse);
                                    }

                                    sweetAlertDialog.cancel();
                                }
                            });
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }catch (JSONException e){

                }


            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                /*Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();*/
                SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("ERROR")
                        .setContentText(message)
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        });
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                /*Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();*/
                SweetAlertDialog dialog=new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("CANCELLED")
                        .setContentText(message)
                        .showCancelButton(false)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        });
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
package com.a3nlotta.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.a3nlotta.R;
import com.a3nlotta.listener.GetCityListener;
import com.a3nlotta.listener.GetCountryListener;
import com.a3nlotta.listener.GetStateListener;
import com.a3nlotta.model.ProfileModel;
import com.a3nlotta.model.address.CityResponse;
import com.a3nlotta.model.address.CountryResponse;
import com.a3nlotta.model.address.StateResponse;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.ImagePicker.RxImagePicker;
import com.a3nlotta.utils.ImagePicker.Sources;
import com.a3nlotta.utils.SharedPreferencesManager;
import com.a3nlotta.utils.Utilities;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.yalantis.ucrop.UCrop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.rxjava3.functions.Consumer;

public class ProfileActivity extends AppCompatActivity {
    CountryResponse countryResponse;
    CityResponse cityResponse;
    StateResponse stateResponse;

    @BindView(R.id.etFName)
    EditText etFName;
    @BindView(R.id.etLName)
    EditText etLName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etMobileNo)
    EditText etMobileNo;
    /*@BindView(R.id.etDOB)
    EditText etDOB;*/
    private ProfileModel profileModel;

    /*@OnClick(R.id.etDOB)
    void onDobClick(){
        Utilities.getDob(this, new DateSelectListener() {
            @Override
            public void onDateSelected(String date) {
                etDOB.setText(date);
            }
        });
    }*/

    @BindView(R.id.spGender)
    Spinner spGender;

    @BindView(R.id.spCountry)
    Spinner spCountry;
    @BindView(R.id.spState)
    Spinner spState;
    @BindView(R.id.spCity)
    Spinner spCity;

    @BindView(R.id.ivProfilePic)
    ImageView ivProfilePic;

    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }

    @OnClick(R.id.icEditProfilePic)
    public void onEditProfilePicClick(){
        ImagePicker.Companion.with(this)
                .crop(1,1)	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @OnClick(R.id.btChangePassword)
    public void onChangePasswordClick(){
        startActivity(new Intent(ProfileActivity.this,ChangePasswordActivity.class));
    }
    @OnClick(R.id.btSave)
    public void onSaveClick(){
        if(TextUtils.isEmpty(etFName.getText())){
            Toast.makeText(this, R.string.enter_first_name,Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etLName.getText())){
            Toast.makeText(this, R.string.enter_last_name,Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etEmail.getText())){
            Toast.makeText(this, R.string.enter_email,Toast.LENGTH_LONG).show();
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
        }else {
            saveUserDetails();
        }
    }

    private void saveUserDetails() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",etFName.getText());
            jsonObject.put("last_name",etLName.getText());
            jsonObject.put("email",etEmail.getText());
            jsonObject.put("contact",etMobileNo.getText());
            jsonObject.put("country",spCountry.getSelectedItem().toString());
            jsonObject.put("state",spState.getSelectedItem().toString());
            jsonObject.put("city",spCity.getSelectedItem().toString());
            jsonObject.put("gender",spGender.getSelectedItem().toString());
            jsonObject.put("profile_img","");


            AlertDialog dialog = Utilities.getProgressDialog(ProfileActivity.this, getString(R.string.request_submiting));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.UPDATE_PROFILE, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            Toasty.success(ProfileActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                            getProfile();
                        }else{
                            Toasty.error(ProfileActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(ProfileActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

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

       /* etDOB.setFocusable(false);
        etDOB.setClickable(true);*/



        //setProfileData();
        getProfile();

        getCountry();

    }

    private void setProfileData() {
        profileModel = SharedPreferencesManager.getProfile();
        if(profileModel!=null){
            if(!TextUtils.isEmpty(profileModel.getProfileImg()))
                Glide.with(ProfileActivity.this).load(profileModel.getProfileImg()).error(R.drawable.ic_avtar).into(ivProfilePic);

            if(!TextUtils.isEmpty(profileModel.getName()))
                etFName.setText(profileModel.getName());
            if(!TextUtils.isEmpty(profileModel.getLastName()))
                etLName.setText(profileModel.getLastName());
            if(!TextUtils.isEmpty(profileModel.getContact()))
                etMobileNo.setText(profileModel.getContact());
            else
                etMobileNo.setEnabled(true);

            if(!TextUtils.isEmpty(profileModel.getEmail()))
                etEmail.setText(profileModel.getEmail());
            else
                etEmail.setEnabled(true);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
           if(data!=null) {
               Uri uri = data.getData();
               ivProfilePic.setImageURI(uri);
               uploadImage(uri);
           }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(Uri uri) {
        File file = new File(uri.getPath());

        if(file!=null){
            try{

                Bitmap photo = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photo = Utilities.getBitmapFromGalleryUri(this,uri,720.0);
                }else{
                    photo = Utilities.uriToBitmap(this, uri);
                    if (photo == null) {
                        photo = BitmapFactory.decodeFile(uri.getPath());
                        if (photo == null) {
                            try {
                                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                try{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("file", Utilities.bitmapToBase64(photo, file.getName()));


                    AlertDialog dialog = Utilities.getProgressDialog(ProfileActivity.this, getString(R.string.request_submiting));
                    dialog.show();
                    WebAPI.call(this, jsonObject, Constants.UPLOAD_PROFILE_PIC, Request.Method.POST, new VolleyManager() {

                        @Override
                        public void onResponse(JSONObject response) {
                            dialog.cancel();
                            try {
                                if(response.getBoolean("success")){
                                    Toasty.success(ProfileActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                                    getProfile();
                                }else{
                                    Toasty.error(ProfileActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.cancel();
                            Toasty.error(ProfileActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
                /*WebAPI.multipartReqWithVolly(this, Constants.UPLOAD_PROFILE_PIC, file, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        try {
                            String jsonString = new String(((NetworkResponse)response).data, HttpHeaderParser.parseCharset(((NetworkResponse)response).headers));
                            new JSONObject(jsonString);
                        }catch (Exception e){

                        }
                        dialog.cancel();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.cancel();
                    }
                });*/
            }catch (Exception e){
                e.printStackTrace();

            }
        }
    }

    private void getProfile() {
        try{
            JSONObject jsonObject = new JSONObject();

            AlertDialog dialog = Utilities.getProgressDialog(this, getString(R.string.loading));
            dialog.show();

            WebAPI.call(this, jsonObject, Constants.GET_USER_DATA, Request.Method.GET, new VolleyManager() {
                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            JSONArray array = response.getJSONArray("data");
                            JSONObject profile = array.getJSONObject(0);

                            SharedPreferencesManager.setProfile(profile.toString());

                            setProfileData();

                        }else{
                            Toasty.error(ProfileActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(ProfileActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
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

                            if(profileModel!=null && !TextUtils.isEmpty(profileModel.getState())) {
                                int statePos=stateResponse.getSelectedStatePos(profileModel.getState());
                                if(statePos!=0)
                                    spState.setSelection( statePos+ 1);
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



                            if(profileModel!=null && !TextUtils.isEmpty(profileModel.getCity())) {
                                int cityPos=cityResponse.getSelectedCityPos(profileModel.getCity());
                                if(cityPos!=0)
                                    spCity.setSelection(cityPos + 1);
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
    
    private void showSelectImageDialog(){
        androidx.appcompat.app.AlertDialog.Builder sayWindows = new androidx.appcompat.app.AlertDialog.Builder(
                this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.camera_gallery_dialog_layout, null);
        sayWindows.setView(dialogView);

        androidx.appcompat.app.AlertDialog mAlertDialog = sayWindows.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mAlertDialog.show();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.setCancelable(false);

        LinearLayout linLayCamera = dialogView.findViewById(R.id.linLayCamera);
        LinearLayout linLayGallery = dialogView.findViewById(R.id.linLayGallery);



        linLayCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxImagePicker.with(ProfileActivity.this).requestImage(Sources.CAMERA).subscribe(new Consumer<Uri>() {
                    @Override
                    public void accept(Uri uris) throws Exception {
                        cropImage(uris);
                    }
                });
                mAlertDialog.dismiss();
            }
        });
        linLayGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxImagePicker.with(ProfileActivity.this).requestImage(Sources.GALLERY).subscribe(new Consumer<Uri>() {
                    @Override
                    public void accept(Uri uris) throws Exception {
                        cropImage(uris);
                    }
                });
                mAlertDialog.dismiss();
            }
        });


        mAlertDialog.show();
    }

    private void cropImage(Uri uris) {
        File f =new File(uris.getPath());

        UCrop.of(uris, Uri.fromFile(new File(getCacheDir(), f.getName())))
                .withAspectRatio(1, 1)
                .withMaxResultSize(300, 300)
                .start(ProfileActivity.this);
    }
}
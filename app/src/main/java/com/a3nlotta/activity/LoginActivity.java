package com.a3nlotta.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a3nlotta.utils.SharedPreferencesManager;
import com.a3nlotta.utils.Utilities;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.a3nlotta.R;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.VolleyManager;
import com.a3nlotta.utils.WebAPI;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tvSignUp)
    TextView tvSignUp;

    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.login_button)
    LoginButton loginButton;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @OnClick(R.id.tvForgotPassword)
    public void onForgotPasswordClick(){
        if(TextUtils.isEmpty(etUserName.getText())){
            Toast.makeText(this, R.string.enter_user_name,Toast.LENGTH_LONG).show();
        }else{
            forgotPassword();
        }
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            // Google Sign In was successful, authenticate with Firebase
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            Log.d("Google login", "firebaseAuthWithGoogle:" + account.getId());
                            firebaseAuthWithGoogle(account.getIdToken());
                        } catch (ApiException e) {
                            // Google Sign In failed, update UI appropriately
                            Log.w("Google login", "Google sign in failed", e);
                        }
                    }else{

                    }
                }
            });
    private void forgotPassword() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email",etUserName.getText());

            AlertDialog dialog = Utilities.getProgressDialog(LoginActivity.this, getString(R.string.authenticating_user));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.FORGOT_PASSWORD, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            Toasty.success(LoginActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();

                        }else{
                            Toasty.error(LoginActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(LoginActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.ivGoogle)
    void onGoogleClick(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        someActivityResultLauncher.launch(signInIntent);
    }
    @OnClick(R.id.ivFacebook)
    void onFacebookClick(){
        loginButton.performClick();
    }

    @OnClick(R.id.btSignIn)
    void onSignInClick(){
        if(TextUtils.isEmpty(etUserName.getText())){
            Toast.makeText(this, R.string.enter_user_name,Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(etPassword.getText())){
            Toast.makeText(this, R.string.enter_password,Toast.LENGTH_LONG).show();
        }else {
            loginApi();
        }

    }

    private void loginApi() {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email",etUserName.getText());
            jsonObject.put("password",etPassword.getText());

            AlertDialog dialog = Utilities.getProgressDialog(LoginActivity.this, getString(R.string.authenticating_user));
            dialog.show();
            WebAPI.call(this, jsonObject, Constants.LOGIN, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        if(response.getBoolean("success")){
                            JSONObject user = response.getJSONObject("data");

                            String name = user.getString("name");
                            String token = user.getString("token");
                            String id = user.getString("id");
                            String imgUrl = user.getString("img_url");

                            SharedPreferencesManager.setLoginToken(token);
                            SharedPreferencesManager.setUserId(id);
                            SharedPreferencesManager.setName(name);
                            SharedPreferencesManager.setImgUrl(imgUrl);
                            SharedPreferencesManager.setEmail(etUserName.getText().toString());
                            SharedPreferencesManager.setIsLogin(true);

                            Toasty.success(LoginActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(LoginActivity.this, LandingActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }else{
                            Toasty.error(LoginActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(LoginActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tvSignUp)
    void onSigUpClick(){
        startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        tvSignUp.setText(Html.fromHtml(getString(R.string.dont_hav_account_sign_up), Html.FROM_HTML_MODE_COMPACT));

        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance().signOut();
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("facebook login", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("facebook login", "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("facebook login", "facebook:onError", error);
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Google login", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user,"google");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Google login", "signInWithCredential:failure", task.getException());
                            updateUI(null,"google");
                        }
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("facebook login", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user,"facebook");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("facebook login", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null,"facebook");
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user,String social_type) {
        if(user!=null){
            try{
                JSONObject jsonObject = new JSONObject();
                UserInfo tempUserInfo;
                if(user.getProviderData().size()>1){
                    tempUserInfo = user.getProviderData().get(1);
                }else if(user.getProviderData().size()>0){
                    tempUserInfo = user.getProviderData().get(0);
                }else{
                    tempUserInfo=user;
                }

                jsonObject.put("email",tempUserInfo.getEmail());
                jsonObject.put("username",tempUserInfo.getUid());
                jsonObject.put("contact",tempUserInfo.getPhoneNumber());
                jsonObject.put("name",tempUserInfo.getDisplayName());
                jsonObject.put("image",tempUserInfo.getPhotoUrl());
                jsonObject.put("social_type",social_type);

                if(social_type.equalsIgnoreCase("google"))
                    Auth.GoogleSignInApi.signOut(mGoogleSignInClient.asGoogleApiClient()).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {

                                }
                            });
                else if(social_type.equalsIgnoreCase("facebook"))
                    LoginManager.getInstance().logOut();

                FirebaseAuth.getInstance().signOut();

                AlertDialog dialog = Utilities.getProgressDialog(LoginActivity.this, getString(R.string.authenticating_user));
                dialog.show();
                WebAPI.call(this, jsonObject, Constants.SOCIAL_LOGIN, Request.Method.POST, new VolleyManager() {

                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.cancel();
                        try {
                            if(response.getBoolean("success")){
                                JSONObject user = response.getJSONObject("data");


                                if(user.has("name")) {
                                    String name = user.getString("name");
                                    SharedPreferencesManager.setName(name);
                                }

                                if(user.has("token")) {
                                    String token = user.getString("token");
                                    SharedPreferencesManager.setLoginToken(token);
                                }
                                if(user.has("id")) {
                                    String id = user.getString("id");
                                    SharedPreferencesManager.setUserId(id);
                                }
                                if(user.has("img_url")) {
                                    String imgUrl = user.getString("img_url");
                                    SharedPreferencesManager.setImgUrl(imgUrl);

                                }

                                SharedPreferencesManager.setEmail(jsonObject.getString("email"));
                                SharedPreferencesManager.setIsLogin(true);

                                Toasty.success(LoginActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(LoginActivity.this, LandingActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }else{
                                Toasty.error(LoginActivity.this, response.getString("msg"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.cancel();
                        Toasty.error(LoginActivity.this, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
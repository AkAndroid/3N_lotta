package com.a3nlotta.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.a3nlotta.model.ProfileModel;
import com.google.gson.Gson;


public class SharedPreferencesManager {

    private static final String TAG = SharedPreferencesManager.class.getSimpleName();

    private static SharedPreferences sharedPrefMgr;
    private static SharedPreferences.Editor preferenceEditor;
    private static Context appContext;

    public static void createSharedPreferences(final Context appContext)
    {
        SharedPreferencesManager.appContext = appContext;
        sharedPrefMgr = PreferenceManager.getDefaultSharedPreferences(SharedPreferencesManager.appContext);
        preferenceEditor = sharedPrefMgr.edit();
        Log.e(TAG, "Shared_Preference_Created");

    }

    public static void clearPreference()
    {
        preferenceEditor = sharedPrefMgr.edit();
        preferenceEditor.clear();
        preferenceEditor.apply();
        Log.d(TAG, "clearPreference-->Shared Preference Cleared");
    }

    public static String getUserId() {
        return sharedPrefMgr.getString("userid", "");
    }

    public static void setUserId(String id)
    {
        preferenceEditor.putString("userid", id);
        preferenceEditor.apply();
    }

    public static String getName() {
        return sharedPrefMgr.getString("user_name", "");
    }

    public static void setName(String name)
    {
        preferenceEditor.putString("user_name", name);
        preferenceEditor.apply();
    }

    public static String getImgUrl() {
        return sharedPrefMgr.getString("user_img_url", "");
    }

    public static void setImgUrl(String url)
    {
        preferenceEditor.putString("user_img_url", url);
        preferenceEditor.apply();
    }
    public static String getEmail() {
        return sharedPrefMgr.getString("user_email", "");
    }

    public static void setEmail(String email)
    {
        preferenceEditor.putString("user_email", email);
        preferenceEditor.apply();
    }

    public static String getLoginType()
    {
        return sharedPrefMgr.getString("login_type", "");
    }

    public static void setLoginType(String login_type)
    {
        preferenceEditor.putString("login_type", login_type);
        preferenceEditor.commit();
    }

    public static String getLoginToken()
    {
        return sharedPrefMgr.getString("token", Constants.STATIC_TOKEN);
    }

    public static void setLoginToken(String token)
    {
        preferenceEditor.putString("token", token);
        preferenceEditor.commit();

    }


    public static Boolean isLogin()
    {
        return sharedPrefMgr.getBoolean("login_status", false);
    }

    public static void setIsLogin(Boolean status)
    {
        preferenceEditor.putBoolean("login_status", status);
        preferenceEditor.apply();

    }

    public static String getFCMToken()
    {
        return sharedPrefMgr.getString("fcm_token", "");
    }
    public static void setFCMToken(String token) {
        preferenceEditor.putString("fcm_token", token);
        preferenceEditor.commit();
    }

    public static ProfileModel getProfile(){
        if(!TextUtils.isEmpty(sharedPrefMgr.getString("profile", ""))){
            return new Gson().fromJson(sharedPrefMgr.getString("profile", ""),ProfileModel.class);
        }
        return null;
    }
    public static void setProfile(String profile) {
        preferenceEditor.putString("profile", profile);
        preferenceEditor.commit();
    }

    public static String getCurrency(){
        if(getProfile()!=null && !TextUtils.isEmpty(getProfile().getCurrency())){
            return  getProfile().getCurrency();
        }else{
            return "$";
        }
    }
}

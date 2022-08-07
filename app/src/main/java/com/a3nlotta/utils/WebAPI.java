package com.a3nlotta.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.a3nlotta.R;
import com.a3nlotta.activity.LandingActivity;
import com.a3nlotta.activity.SignUpActivity;
import com.a3nlotta.listener.GetCityListener;
import com.a3nlotta.listener.GetCountryListener;
import com.a3nlotta.listener.GetStateListener;
import com.a3nlotta.model.address.CityResponse;
import com.a3nlotta.model.address.CountryResponse;
import com.a3nlotta.model.address.StateResponse;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class WebAPI {

    JSONObject jsonparam;
    VolleyManager volleyManagerListenter;
    String actionname;
    int requestMethod;
    private String tag_json_obj = "jobj_req";
    private String mTokan;

    public static void call(Context context,JSONObject jsonParam, String url, int requestMethod,VolleyManager callback){

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(requestMethod, url,
                jsonParam, callback, callback) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + SharedPreferencesManager.getLoginToken());
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq, "Web API call method");
    }


    public static void getCountry(Context context, GetCountryListener callback) {
        try{
            JSONObject jsonObject = new JSONObject();

            AlertDialog dialog = Utilities.getProgressDialog(context, context.getString(R.string.loading));
            dialog.show();
            WebAPI.call(context, jsonObject, Constants.GET_COUNTRY, Request.Method.GET, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        callback.onResponse(new Gson().fromJson(response.toString(),CountryResponse.class));
                    } catch (Exception e) {
                        callback.onResponse(null);
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    callback.onResponse(null);
                    Toasty.error(context, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            callback.onResponse(null);
        }
    }
    public static void getState(Context context, Integer countryID, GetStateListener callback) {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",countryID);

            AlertDialog dialog = Utilities.getProgressDialog(context, context.getString(R.string.loading));
            dialog.show();
            WebAPI.call(context, jsonObject, Constants.GET_STATE, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        try {
                            callback.onResponse(new Gson().fromJson(response.toString(), StateResponse.class));
                        } catch (Exception e) {
                            callback.onResponse(null);
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(context, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getCity(Context context, Integer stateID, GetCityListener callback) {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",stateID);

            AlertDialog dialog = Utilities.getProgressDialog(context, context.getString(R.string.loading));
            dialog.show();
            WebAPI.call(context, jsonObject, Constants.GET_CITY, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                    dialog.cancel();
                    try {
                        try {
                            callback.onResponse(new Gson().fromJson(response.toString(), CityResponse.class));
                        } catch (Exception e) {
                            callback.onResponse(null);
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.cancel();
                    Toasty.error(context, Utilities.getVolleyError(error), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void multipartReqWithVolly(final Context context, String url,
                                              final File file, final Response.Listener listener, final Response.ErrorListener listenerError) {
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, listener, listenerError) {
            @Override
            protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer " + SharedPreferencesManager.getLoginToken());
                return headers;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();

                if (file != null) {
                    int size = (int) file.length();
                    byte[] bytes = new byte[size];
                    try {
                        BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                        buf.read(bytes, 0, bytes.length);
                        buf.close();
                    } catch (FileNotFoundException e) {

                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    params.put("file", new DataPart(file.getName(), bytes, "*/*"));
                }
                return params;
            }


        };

        VolleySingleton.getInstance(context).addToRequestQueue(multipartRequest);
    }

    public static void sendRegistrationToServer(Context context,String token) {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("device_token",token);
            WebAPI.call(context, jsonObject, Constants.FCM_UPDATE, Request.Method.POST, new VolleyManager() {

                @Override
                public void onResponse(JSONObject response) {
                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("FCM_update",Utilities.getVolleyError(error));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

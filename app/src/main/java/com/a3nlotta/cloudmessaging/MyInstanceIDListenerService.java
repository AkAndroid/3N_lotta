/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.a3nlotta.cloudmessaging;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.a3nlotta.R;
import com.a3nlotta.activity.LandingActivity;
import com.a3nlotta.utils.Constants;
import com.a3nlotta.utils.SharedPreferencesManager;
import com.a3nlotta.utils.WebAPI;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyInstanceIDListenerService extends FirebaseMessagingService {

    private static final String TAG = "MyInstanceIDLS";

    @Override
    public void onNewToken(String token) {

        SharedPreferencesManager.setFCMToken(token);

        WebAPI.sendRegistrationToServer(this,token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            sendNotification1(remoteMessage);
        else
            sendNotification(remoteMessage);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification1(RemoteMessage remoteMessage) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel("CHANNEL_1", "Example channel", NotificationManager.IMPORTANCE_DEFAULT);
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this, "CHANNEL_1");

        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(getApplicationContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String title= remoteMessage.getData().get("title") != null ? remoteMessage.getData().get("title") :
                remoteMessage.getNotification()!=null ? remoteMessage.getNotification().getTitle() : getString(R.string.app_name);

        String body= remoteMessage.getData().get("body") != null ? remoteMessage.getData().get("body") :
                remoteMessage.getNotification()!=null ? remoteMessage.getNotification().getBody() : "";

        notification
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setNumber(3);

        if(Integer.parseInt(SharedPreferencesManager.getProfile().getVibration())==1)
            notification.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        if(Integer.parseInt(SharedPreferencesManager.getProfile().getSounds())==1)
            notification.setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.notify(new Random().nextInt(), notification.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void makeNotificationChannel(String id, String name, int importance) {
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setShowBadge(true); // set false to disable badges, Oreo exclusive

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.createNotificationChannel(channel);
    }

    private void sendNotification(RemoteMessage remoteMessage) {


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this,"");
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(getApplicationContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String title= remoteMessage.getData().get("title") != null ? remoteMessage.getData().get("title") :
                remoteMessage.getNotification()!=null ? remoteMessage.getNotification().getTitle() : getString(R.string.app_name);

        String body= remoteMessage.getData().get("body") != null ? remoteMessage.getData().get("body") :
                remoteMessage.getNotification()!=null ? remoteMessage.getNotification().getBody() : "";

        notification
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setNumber(3);

        if(Integer.parseInt(SharedPreferencesManager.getProfile().getVibration())==1)
            notification.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        if(Integer.parseInt(SharedPreferencesManager.getProfile().getSounds())==1)
            notification.setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(new Random().nextInt(), notification.build());
    }
}

package com.a3nlotta.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.a3nlotta.R;
import com.a3nlotta.utils.SharedPreferencesManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ReferEarnActivity extends AppCompatActivity {

    String shareText="Hey! I am referring you 3nLotta best lottery mobile app, use my reference code ### while joining";

    @OnClick(R.id.ivBack)
    public void onBackClick(){
        onBackPressed();
    }

    @OnClick(R.id.ivFacebook)
    public void onFacebookClick(){
        try {
            Intent intent1 = new Intent();
            intent1.setPackage("com.facebook.katana");
            intent1.setAction("android.intent.action.SEND");
            intent1.setType("text/plain");
            intent1.putExtra("android.intent.extra.TEXT", shareText);
            startActivity(intent1);
        } catch (Exception e) {
            // If we failed (not native FB app installed), try share through SEND
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + shareText;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            startActivity(intent);
        }
    }

    @OnClick(R.id.ivWhatsapp)
    public void onWhatsappClick(){
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toasty.error(this,"Whatsapp have not been installed.");
        }
    }
    @OnClick(R.id.btRefer)
    public void onReferClick(){
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(whatsappIntent);

    }
    @OnClick(R.id.ivInstagram)
    public void onInstaClick(){
        try {
            Intent shareOnAppIntent = new Intent();
            shareOnAppIntent .setAction(Intent.ACTION_SEND);
            shareOnAppIntent .putExtra(Intent.EXTRA_TEXT,shareText);
            shareOnAppIntent .setType("text/plain");
            shareOnAppIntent .setPackage("com.instagram.android");
            startActivity(shareOnAppIntent );
        } catch (Exception e) {
            e.printStackTrace();
            Toasty.error(this, "APP is not installed").show();
        }
    }
    @OnClick(R.id.ivMessenger)
    public void onMessengerClick(){
        try {
            Intent shareOnAppIntent = new Intent();
            shareOnAppIntent .setAction(Intent.ACTION_SEND);
            shareOnAppIntent .putExtra(Intent.EXTRA_TEXT,shareText);
            shareOnAppIntent .setType("text/plain");
            shareOnAppIntent .setPackage("com.facebook.orca");
            startActivity(shareOnAppIntent );
        } catch (Exception e) {
            e.printStackTrace();
            Toasty.error(this, "APP is not installed").show();
        }
    }
    @OnClick(R.id.ivEmail)
    public void onEmailClick(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", SharedPreferencesManager.getEmail(), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Referring 3nLotta App");
        emailIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(emailIntent, "Send Email..."));
    }
    @OnClick(R.id.ivTwitter)
    public void onTwitterClick(){
        try {
            Intent shareOnAppIntent = new Intent();
            shareOnAppIntent .setAction(Intent.ACTION_SEND);
            shareOnAppIntent .putExtra(Intent.EXTRA_TEXT,shareText);
            shareOnAppIntent .setType("text/plain");
            shareOnAppIntent .setPackage("com.twitter.android");
            startActivity(shareOnAppIntent );
        } catch (Exception e) {
            e.printStackTrace();
            Toasty.error(this, "APP is not installed").show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_earn);
        ButterKnife.bind(this);

        shareText=shareText.replace("###","ABCD1234");

    }
}
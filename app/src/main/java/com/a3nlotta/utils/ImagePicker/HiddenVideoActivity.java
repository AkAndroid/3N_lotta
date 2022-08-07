package com.a3nlotta.utils.ImagePicker;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HiddenVideoActivity extends Activity {

    private static final String KEY_CAMERA_PICTURE_URL = "cameraPictureUrl";

    public static final String VIDEO_SOURCE = "video_source";
    public static final String ALLOW_MULTIPLE_VIDEO = "allow_multiple_video";

    private static final int SELECT_VIDEO = 100;
    private static final int TAKE_VIDEO = 101;

    private Uri cameraVideoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_CAMERA_PICTURE_URL, cameraVideoUrl);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        cameraVideoUrl = savedInstanceState.getParcelable(KEY_CAMERA_PICTURE_URL);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            handleIntent(getIntent());
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_VIDEO:
                    handleGalleryResult(data);
                    break;
                case TAKE_VIDEO:
                    RxImagePicker.with(this).onPicked(cameraVideoUrl);
                    break;
            }
        }
        finish();
    }

    private void handleGalleryResult(Intent data) {
        if (getIntent().getBooleanExtra(ALLOW_MULTIPLE_VIDEO, false)) {
            ArrayList<Uri> imageUris = new ArrayList<>();
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    imageUris.add(clipData.getItemAt(i).getUri());
                }
            } else {
                imageUris.add(data.getData());
            }
            RxImagePicker.with(this).onPicked(imageUris);
        } else {
            RxImagePicker.with(this).onPicked(data.getData());
        }
    }

    private void handleIntent(Intent intent) {
        if (!checkPermission()) {
            return;
        }

        Sources sourceType = Sources.values()[intent.getIntExtra(VIDEO_SOURCE, 0)];
        int chooseCode = 0;
        Intent pictureChooseIntent = null;

        switch (sourceType) {
            case CAMERA:
                cameraVideoUrl = createImageUri();
                pictureChooseIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pictureChooseIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraVideoUrl);
                pictureChooseIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 5025*1024*1024);
                pictureChooseIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                chooseCode = TAKE_VIDEO;
                break;
            case GALLERY:
                pictureChooseIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    pictureChooseIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                    pictureChooseIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                } else {
                    pictureChooseIntent = new Intent(Intent.ACTION_GET_CONTENT);
                }

                pictureChooseIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                pictureChooseIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pictureChooseIntent.setType("video/*");
                chooseCode = SELECT_VIDEO;
                break;
        }

        startActivityForResult(pictureChooseIntent, chooseCode);
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HiddenVideoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            return false;
        } else {
            return true;
        }
    }

    private Uri createImageUri() {
        ContentResolver contentResolver = getContentResolver();
        ContentValues cv = new ContentValues();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        cv.put(MediaStore.Images.Media.TITLE, timeStamp);
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
    }


}
package com.a3nlotta.utils.ImagePicker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;


public class RxImagePicker {

    private static RxImagePicker instance;

    public static synchronized RxImagePicker with(Context context) {
        if (instance == null) {
            instance = new RxImagePicker(context.getApplicationContext());
        }
        return instance;
    }

    private Context context;
    private PublishSubject<Uri> publishSubject;
    private PublishSubject<List<Uri>> publishSubjectMultipleImages;

    private RxImagePicker(Context context) {
        this.context = context;
    }

    public Observable<Uri> getActiveSubscription() {
        return publishSubject;
    }

    public Observable<Uri> requestImage(Sources imageSource) {
        publishSubject = PublishSubject.create();
        startImagePickHiddenActivity(imageSource.ordinal(), false);
        return publishSubject;
    }

    public Observable<Uri> requestVideo(Sources videoSource) {
        publishSubject = PublishSubject.create();
        startVideoPickHiddenActivity(videoSource.ordinal(), false);
        return publishSubject;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Observable<List<Uri>> requestMultipleImages() {
        publishSubjectMultipleImages = PublishSubject.create();
        startImagePickHiddenActivity(Sources.GALLERY.ordinal(), true);
        return publishSubjectMultipleImages;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public Observable<List<Uri>> requestMultipleVideo() {
        publishSubjectMultipleImages = PublishSubject.create();
        startVideoPickHiddenActivity(Sources.GALLERY.ordinal(), true);
        return publishSubjectMultipleImages;
    }

    void onPicked(Uri uri) {
        if (publishSubject != null) {
            publishSubject.onNext(uri);
            publishSubject.onComplete();
           // publishSubject.onError(new Throwable());
        }
    }

    void onPicked(List<Uri> uris) {
        if (publishSubjectMultipleImages != null) {
            publishSubjectMultipleImages.onNext(uris);
            publishSubjectMultipleImages.onComplete();
            //publishSubjectMultipleImages.onError(new Throwable());
        }
    }

    private void startImagePickHiddenActivity(int imageSource, boolean allowMultipleImages) {
        Intent intent = new Intent(context, HiddenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(HiddenActivity.ALLOW_MULTIPLE_IMAGES, allowMultipleImages);
        intent.putExtra(HiddenActivity.IMAGE_SOURCE, imageSource);
        context.startActivity(intent);
    }

    private void startVideoPickHiddenActivity(int imageSource, boolean allowMultipleVideo) {
        Intent intent = new Intent(context, HiddenVideoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(HiddenVideoActivity.ALLOW_MULTIPLE_VIDEO, allowMultipleVideo);
        intent.putExtra(HiddenVideoActivity.VIDEO_SOURCE, imageSource);
        context.startActivity(intent);
    }

}


package com.a3nlotta.utils;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.a3nlotta.R;
import com.a3nlotta.activity.ProfileActivity;
import com.a3nlotta.listener.DateSelectListener;
import com.a3nlotta.listener.OnDialogButtonClickListener;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utilities {

    public static AlertDialog getProgressDialog(Context context, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null);

        TextView tvMsg = view.findViewById(R.id.tvMsg);
        if(!TextUtils.isEmpty(msg))
            tvMsg.setText(msg);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        return dialog;
    }

    public static String getVolleyError(VolleyError error) {
        if (error == null || error.networkResponse == null) {
            try {
                if (error != null) {
                    if (error.getClass() == NoConnectionError.class) {
                        return "Please check internet connection";
                    }
                }
                return error != null && error.getMessage()!=null ? error.getMessage() : "Some thing went wrong";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Some thing went wrong";
        }

        String body;

        try {
            body = new String(error.networkResponse.data, "UTF-8");
            return Utilities.getErrorMsg(body);
        } catch (UnsupportedEncodingException e) {
            // exception
        }

        return "Some thing went wrong";
    }

    public static String getErrorMsg(String body) {
        String returnMessage=body;

        try {
            JSONObject bodyObject = new JSONObject(body);
            if (bodyObject.has("msg")) {
                returnMessage=bodyObject.getString("msg");
            }else if (bodyObject.has("message")) {
                returnMessage=bodyObject.getString("message");
            }
        }catch (Exception e){

        }

        return returnMessage;
    }

    public static void getDob(Context context, DateSelectListener callback) {
        if (context != null) {

            final Date[] value = {new Date()};
            final Calendar cal = Calendar.getInstance();
            cal.setTime(value[0]);
            DatePickerDialog datePickerDialogStart = new DatePickerDialog(context, android.app.AlertDialog.THEME_HOLO_LIGHT,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view,
                                              int y, int m, int d) {
                            cal.set(Calendar.YEAR, y);
                            cal.set(Calendar.MONTH, m);
                            cal.set(Calendar.DAY_OF_MONTH, d);
                            SimpleDateFormat sdf = new SimpleDateFormat(Constants.dateFormat_DD_MM_YYYY, Locale.getDefault());

                            callback.onDateSelected(sdf.format(cal.getTime()));
                        }
                    }
                    , cal.get(Calendar.YEAR)-18, cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH) + 1);

            Calendar maxCal = Calendar.getInstance();
            maxCal.add(Calendar.YEAR, -18);

            Calendar minCal = Calendar.getInstance();
            minCal.add(Calendar.YEAR, -100);

            datePickerDialogStart.getDatePicker().setMaxDate(maxCal.getTimeInMillis());
            datePickerDialogStart.getDatePicker().setMinDate(minCal.getTimeInMillis());
            datePickerDialogStart.getDatePicker().setCalendarViewShown(false);
            datePickerDialogStart.show();
        }
    }

    public static String convertDate(String date,String fromFormat,String toFormat) {

        try {
            DateFormat inputFormat = new SimpleDateFormat(fromFormat);
            DateFormat outputFormat = new SimpleDateFormat(toFormat);
            Date date1 = null;
            date1 = inputFormat.parse(date);
            String outputDateStr = outputFormat.format(date1);
            return outputDateStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static void showCustomAlert(@NonNull Context context, @NonNull CharSequence msg,boolean showPositiveButton,boolean showNegativeButton,OnDialogButtonClickListener listener) {

        androidx.appcompat.app.AlertDialog.Builder sayWindows = new androidx.appcompat.app.AlertDialog.Builder(
                context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.alert_thank_you_dialog_layout, null);
        sayWindows.setView(dialogView);

        androidx.appcompat.app.AlertDialog mAlertDialog = sayWindows.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mAlertDialog.show();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.setCancelable(false);

        ImageView ivClose = (ImageView) dialogView.findViewById(R.id.ivClose);
        Button btPositive = dialogView.findViewById(R.id.btPositive);

        ivClose.setVisibility(showNegativeButton?View.VISIBLE:View.GONE);
        btPositive.setVisibility(showPositiveButton?View.VISIBLE:View.GONE);

        TextView tvMsg = (TextView) dialogView.findViewById(R.id.tvMsg);
        tvMsg.setText(msg);

        btPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.onPositiveClick(v,null);
                mAlertDialog.dismiss();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.onNegativeClick(v,null);
                mAlertDialog.dismiss();
            }
        });

        mAlertDialog.show();
    }

    public static String bitmapToBase64(Bitmap imageBitmap, String name){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String ext = name.substring(name.lastIndexOf(".")+1);

        int quality = 100;

        if(ext.equalsIgnoreCase("png"))
            imageBitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream);
        else if(ext.equalsIgnoreCase("jpg")) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
        }else if(ext.equalsIgnoreCase("jpeg"))
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Bitmap getBitmapFromGalleryUri(Context mContext, Uri uri, Double maxSize)throws IOException {
        int orientation = 0;

        InputStream input = mContext.getContentResolver().openInputStream(uri);
        if (input != null){
            ExifInterface exif = new ExifInterface(input);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            //Log.d("Utils", "rotation value = " + orientation);
            input.close();
        }


        input = mContext.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        try {
            input.close();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = /*(originalSize > maxSize) ? (originalSize / maxSize) :*/ 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//
        input = mContext.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        try {
            input.close();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Matrix matrix = new Matrix();

        //Log.d("Utils", "rotation value = " + orientation);

        int rotationInDegrees = exifToDegrees(orientation);
        //Log.d("Utils", "rotationInDegrees value = " + rotationInDegrees);

        if (orientation != 0) {
            matrix.preRotate(rotationInDegrees);
        }

        int bmpWidth = 0;
        try {
            bmpWidth = bitmap.getWidth();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Bitmap adjustedBitmap = bitmap;
        if (bmpWidth > 0) {
            adjustedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        return adjustedBitmap;

    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }

    public static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }

    public static Bitmap uriToBitmap(Context context, Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    context.getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);

            parcelFileDescriptor.close();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

}

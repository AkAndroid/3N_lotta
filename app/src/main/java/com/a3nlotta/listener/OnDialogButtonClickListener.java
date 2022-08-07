package com.a3nlotta.listener;

import android.view.View;

public interface OnDialogButtonClickListener {
    void onPositiveClick(View v, Object o);
    void onNegativeClick(View v, Object o);
}
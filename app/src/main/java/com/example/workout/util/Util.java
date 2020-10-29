package com.example.workout.util;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class Util {
    public static void makeSnackBar(View view, int textId, int length, int backgroundColorId) {
        Snackbar snackbar = Snackbar.make(view, textId, length);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), backgroundColorId));
        snackbar.show();
    }

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidStringLength(String target, int length) {
        return (!TextUtils.isEmpty(target) && (target.length() >= length));
    }
}

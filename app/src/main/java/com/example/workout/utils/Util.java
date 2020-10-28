package com.example.workout.utils;

import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

public class Util {
    public static void makeSnackBar(View view, int textId, int length, int backgroundColorId) {
        Snackbar snackbar = Snackbar.make(view, textId, length);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), backgroundColorId));
        snackbar.show();
    }
}

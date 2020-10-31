package com.example.workout.interfaces;

import android.net.Uri;

public interface IAddNewWorkoutView extends IError {
    void updateUI(Uri imageUri);
    void success();
}

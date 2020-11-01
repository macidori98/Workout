package com.example.workout.interfaces;

import android.content.Intent;
import android.net.Uri;

public interface IAddNewWorkoutView extends IError {
    void updateUI(Uri imageUri);

    void success();

    void startTakePhoto(Intent intent);
}

package com.example.workout.interfaces;

import android.content.Context;
import android.content.Intent;

public interface IAddNewWorkoutPresenter extends IBasePresenter {
    void handleAddNewWorkout(String workoutName, String calories, String dateOfWorkout, String duration, String imageUri);

    void handlePhoto(int requestCode, int resultCode, Intent data, Context context);

    void setTakePhotoIntent(Intent intent, Context context);
}

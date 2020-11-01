package com.example.workout.interfaces;

import android.content.Intent;

public interface IAddNewWorkoutPresenter extends IBasePresenter {
    void handleAddNewWorkout(String workoutName, String calories, String dateOfWorkout, String duration, String imageUri);

    void handlePickPhoto(int requestCode, int resultCode, Intent data);
}

package com.example.workout.interfaces;

import android.content.Intent;

public interface IAddNewWorkoutPresenter {
    void handleAddNewWorkout(String workoutName, String calories, String dateOfWorkout, String duration, String imageUri);

    void failure(int msgId);

    void success();

    void handlePickPhoto(int requestCode, int resultCode, Intent data);
}

package com.example.workout.interfaces;

public interface IAddNewWorkoutPresenter {
    void handleAddNewWorkout(String workoutName, String calories, String dateOfWorkout, String duration);
    void failure(int msgId);
    void success();
}

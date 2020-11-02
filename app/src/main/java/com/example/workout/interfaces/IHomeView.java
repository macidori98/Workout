package com.example.workout.interfaces;

import com.example.workout.model.Workout;

import java.util.List;

public interface IHomeView extends IError {
    void updateUI(List<Workout> workoutList, int visibility);

    void logout(int msgId);
}

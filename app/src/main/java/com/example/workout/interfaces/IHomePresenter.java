package com.example.workout.interfaces;

import android.content.Context;

import com.example.workout.model.Workout;

import java.util.List;

public interface IHomePresenter {
    void handleDataDownload();

    void sendDataList(List<Workout> workoutList);

    void handleLogout(Context context);

    void logoutSuccess();
}

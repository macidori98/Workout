package com.example.workout.presenter;

import android.content.Context;

import com.example.workout.R;
import com.example.workout.database.FirebaseDb;
import com.example.workout.interfaces.IHomePresenter;
import com.example.workout.interfaces.IHomeView;
import com.example.workout.model.Workout;
import com.example.workout.util.GlobalValues;
import com.example.workout.util.Util;

import java.util.Collections;
import java.util.List;

public class HomePresenter implements IHomePresenter {

    private final IHomeView homeView;

    public HomePresenter(IHomeView homeView) {
        this.homeView = homeView;
    }

    @Override
    public void handleDataDownload() {
        FirebaseDb.getInstance().getWorkoutHistory(this);
    }

    @Override
    public void sendDataList(List<Workout> workoutList) {
        Collections.reverse(workoutList);
        this.homeView.updateUI(workoutList);
    }

    @Override
    public void handleLogout(Context context) {
        FirebaseDb.getInstance().logout(this);
        Util.setSharedPref(context, GlobalValues.EMPTY);
    }

    @Override
    public void logoutSuccess() {
        this.homeView.logout(R.string.logout_success);
    }
}

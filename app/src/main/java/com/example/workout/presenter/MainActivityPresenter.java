package com.example.workout.presenter;

import com.example.workout.database.FirebaseDb;
import com.example.workout.interfaces.IMainActivityPresenter;

public class MainActivityPresenter implements IMainActivityPresenter {
    @Override
    public boolean getCurrentFirebaseUser() {
        return FirebaseDb.getInstance().getFirebaseUser() == null;
    }
}

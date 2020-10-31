package com.example.workout.presenter;

import android.os.Handler;

import androidx.fragment.app.Fragment;

import com.example.workout.database.FirebaseDb;
import com.example.workout.interfaces.IMainActivityPresenter;
import com.example.workout.interfaces.IMainActivityView;
import com.example.workout.util.GlobalValues;
import com.example.workout.view.HomeFragment;
import com.example.workout.view.LoginFragment;

public class MainActivityPresenter implements IMainActivityPresenter {

    private final String TAG = MainActivityPresenter.class.getSimpleName();

    private final IMainActivityView mainActivityView;

    public MainActivityPresenter(IMainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void handleNextView() {
        if (GlobalValues.CHECK == 0) {
            GlobalValues.CHECK = GlobalValues.STORAGE_PERMISSION_CODE;
            if (FirebaseDb.getInstance().getFirebaseUser() == null) {
                this.delaying(new LoginFragment());
            } else {
                this.delaying(new HomeFragment());
            }
        }
    }

    private void delaying(Fragment fragment) {
        new Handler().postDelayed(() -> mainActivityView.showFragment(fragment), 1500);
    }
}

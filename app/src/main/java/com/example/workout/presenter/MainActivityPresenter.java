package com.example.workout.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.example.workout.interfaces.IMainActivityPresenter;
import com.example.workout.interfaces.IMainActivityView;
import com.example.workout.util.GlobalValues;
import com.example.workout.view.HomeFragment;
import com.example.workout.view.LoginFragment;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class MainActivityPresenter implements IMainActivityPresenter {

    private final IMainActivityView mainActivityView;

    public MainActivityPresenter(IMainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void handleNextView(Context context) {
        if (GlobalValues.CHECK == 0) {
            GlobalValues.CHECK = GlobalValues.STORAGE_PERMISSION_CODE;
            if (this.isLoggedInUser(context)) {
                this.delaying(new LoginFragment());
            } else {
                this.delaying(new HomeFragment());
            }
        }
    }

    private void delaying(Fragment fragment) {
        new Handler().postDelayed(() -> this.mainActivityView.showFragment(fragment), GlobalValues.SPLASHSCREEN_LENGTH);
    }

    private boolean isLoggedInUser(Context context) {
        SharedPreferences sharedPreferences = Objects.requireNonNull(context).getSharedPreferences(GlobalValues.USERNAME, MODE_PRIVATE);
        GlobalValues.CURRENT_SESSION = sharedPreferences.getString(GlobalValues.USERNAME, GlobalValues.EMPTY);

        return TextUtils.isEmpty(GlobalValues.CURRENT_SESSION);
    }
}

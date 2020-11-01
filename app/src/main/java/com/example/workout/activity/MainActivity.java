package com.example.workout.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.workout.R;
import com.example.workout.interfaces.IMainActivityPresenter;
import com.example.workout.interfaces.IMainActivityView;
import com.example.workout.presenter.MainActivityPresenter;
import com.example.workout.util.FragmentNavigation;
import com.example.workout.util.GlobalValues;

public class MainActivity extends AppCompatActivity implements IMainActivityView {

    private final String TAG = MainActivity.class.getSimpleName();

    private Animation fromBottom;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_main);

        this.initializeElements();

        this.linearLayout.startAnimation(fromBottom);
    }

    private void initializeElements() {
        this.linearLayout = findViewById(R.id.splashScreen_linearLayout);

        this.fromBottom = AnimationUtils.loadAnimation(this, R.anim.splash_screen_animation);
        this.fromBottom.setDuration(GlobalValues.SPLASHSCREEN_LENGTH);
        this.fromBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(TAG, TAG);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(TAG, TAG);
            }
        });
    }

    @Override
    public void onBackPressed() {
        FragmentNavigation.getInstance(getApplicationContext()).onBackPressed(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IMainActivityPresenter mainActivityPresenter = new MainActivityPresenter(this);
        mainActivityPresenter.handleNextView(this);

        ActivityCompat.requestPermissions(this,
                GlobalValues.PERMISSIONS,
                GlobalValues.STORAGE_PERMISSION_CODE);
    }

    @Override
    public void showFragment(Fragment fragment) {
        FragmentNavigation.getInstance(this).replaceFragment(fragment, R.id.fragment_content);
    }
}
package com.example.workout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workout.R;
import com.example.workout.util.GlobalValues;

public class WorkoutTrackerSplashScreenActivity extends AppCompatActivity {
    public static final String TAG = WorkoutTrackerSplashScreenActivity.class.getSimpleName();

    private Animation fromBottom;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        initializeElements();

        linearLayout.startAnimation(fromBottom);
    }

    private void initializeElements() {
        linearLayout = findViewById(R.id.splashScreen_linearLayout);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.splash_screen_animation);
        fromBottom.setDuration(GlobalValues.SPLASHSCREEN_LENGTH);

        fromBottom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d(WorkoutTrackerSplashScreenActivity.TAG, WorkoutTrackerSplashScreenActivity.TAG);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent mainIntent = new Intent(WorkoutTrackerSplashScreenActivity.this, MainActivity.class);
                WorkoutTrackerSplashScreenActivity.this.startActivity(mainIntent);
                WorkoutTrackerSplashScreenActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d(WorkoutTrackerSplashScreenActivity.TAG, WorkoutTrackerSplashScreenActivity.TAG);
            }
        });
    }
}
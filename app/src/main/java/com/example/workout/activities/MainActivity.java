package com.example.workout.activities;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workout.R;
import com.example.workout.database.DatabaseHelper;
import com.example.workout.views.HomeFragment;
import com.example.workout.views.LoginFragment;
import com.example.workout.utils.FragmentNavigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseHelper.firebaseUser = DatabaseHelper.firebaseAuth.getCurrentUser();
        if (DatabaseHelper.firebaseUser != null) {
            FragmentNavigation.getInstance(this).replaceFragment(new HomeFragment(), R.id.fragment_content);
        } else {
            FragmentNavigation.getInstance(this).replaceFragment(new LoginFragment(), R.id.fragment_content);
        }
    }
}
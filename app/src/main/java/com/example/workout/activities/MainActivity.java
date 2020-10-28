package com.example.workout.activities;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workout.R;
import com.example.workout.database.DatabaseUtils;
import com.example.workout.fragments.HomeFragment;
import com.example.workout.fragments.LoginFragment;
import com.example.workout.utils.FragmentNavigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseUtils.firebaseUser = DatabaseUtils.firebaseAuth.getCurrentUser();
        if (DatabaseUtils.firebaseUser != null) {
            FragmentNavigation.getInstance(this).replaceFragment(new HomeFragment(), R.id.fragment_content);
        } else {
            FragmentNavigation.getInstance(this).replaceFragment(new LoginFragment(), R.id.fragment_content);
        }
    }
}
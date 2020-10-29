package com.example.workout.activity;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workout.R;
import com.example.workout.database.FirebaseDb;
import com.example.workout.interfaces.IMainActivityView;
import com.example.workout.util.FragmentNavigation;
import com.example.workout.view.HomeFragment;
import com.example.workout.view.LoginFragment;

public class MainActivity extends AppCompatActivity implements IMainActivityView {

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
        FirebaseDb.firebaseUser = FirebaseDb.firebaseAuth.getCurrentUser();
        if (FirebaseDb.firebaseUser != null) {
            FragmentNavigation.getInstance(this).replaceFragment(new HomeFragment(), R.id.fragment_content);
        } else {
            FragmentNavigation.getInstance(this).replaceFragment(new LoginFragment(), R.id.fragment_content);
        }
    }
}
package com.example.workout.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workout.R;
import com.example.workout.util.FragmentNavigation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    private FloatingActionButton addFloatingActionButton;
    private RecyclerView workoutHistoryRecyclerView;
    private Button logoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_history_fragment, container, false);

        this.initializeElements(view);
        this.setListeners();

        return view;
    }

    private void initializeElements(View view) {
        this.addFloatingActionButton = view.findViewById(R.id.add_new_workout_floatingActionButton);
        this.workoutHistoryRecyclerView = view.findViewById(R.id.workout_history_recyclerview);
        this.logoutButton = view.findViewById(R.id.button_test);
    }

    private void setListeners() {
        this.logoutButton.setOnClickListener(v -> {
            //TODO: remove this code !!!!
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
        });

        this.addFloatingActionButton.setOnClickListener(v -> FragmentNavigation.getInstance(getContext()).replaceFragment(new AddNewWorkoutFragment(), R.id.fragment_content));
    }
}

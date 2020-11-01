package com.example.workout.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workout.R;
import com.example.workout.adapter.WorkoutHistoryAdapter;
import com.example.workout.interfaces.IHomePresenter;
import com.example.workout.interfaces.IHomeView;
import com.example.workout.model.Workout;
import com.example.workout.presenter.HomePresenter;
import com.example.workout.util.FragmentNavigation;
import com.example.workout.util.GlobalValues;
import com.example.workout.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeFragment extends Fragment implements IHomeView {

    private FloatingActionButton addFloatingActionButton;
    private RecyclerView workoutHistoryRecyclerView;
    private ImageButton logoutImageButton;
    private IHomePresenter homePresenter;
    private ProgressBar progressBar;
    private TextView helloUserTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_history_fragment, container, false);

        this.initializeElements(view);
        this.setListeners();

        return view;
    }

    @Override
    public void informUserError(int msgId) {
        Util.makeSnackBar(getView(), msgId, Snackbar.LENGTH_SHORT, R.color.red);
    }

    @Override
    public void updateUI(List<Workout> workoutList) {
        this.helloUserTextView.setText(GlobalValues.HELLO.concat(GlobalValues.CURRENT_SESSION));

        WorkoutHistoryAdapter workoutHistoryAdapter = new WorkoutHistoryAdapter(workoutList, getContext());
        workoutHistoryAdapter.setOnClickListener(position -> FragmentNavigation.getInstance(getContext()).replaceFragment(new WorkoutDetailsFragment(workoutList.get(position)), R.id.fragment_content));

        this.workoutHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.workoutHistoryRecyclerView.setAdapter(workoutHistoryAdapter);
        this.workoutHistoryRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                workoutHistoryRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void logout(int msgId) {
        Util.makeSnackBar(getView(), msgId, Snackbar.LENGTH_SHORT, R.color.green);
        FragmentNavigation.getInstance(getContext()).replaceFragment(new LoginFragment(), R.id.fragment_content);
    }

    private void initializeElements(View view) {
        this.addFloatingActionButton = view.findViewById(R.id.add_new_workout_floatingActionButton);
        this.workoutHistoryRecyclerView = view.findViewById(R.id.workout_history_recyclerview);
        this.logoutImageButton = view.findViewById(R.id.logout_imageButton);
        this.progressBar = view.findViewById(R.id.progressBar);
        this.helloUserTextView = view.findViewById(R.id.hello_user_TextView);
        this.progressBar.setVisibility(View.VISIBLE);
        this.homePresenter = new HomePresenter(this);
    }

    private void setListeners() {
        this.logoutImageButton.setOnClickListener(v -> homePresenter.handleLogout(getContext()));

        this.addFloatingActionButton.setOnClickListener(v -> FragmentNavigation.getInstance(getContext()).replaceFragment(new AddNewWorkoutFragment(), R.id.fragment_content));

        this.homePresenter.handleDataDownload();
    }
}

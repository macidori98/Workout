package com.example.workout.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.workout.R;
import com.example.workout.interfaces.IAddNewWorkoutPresenter;
import com.example.workout.interfaces.IAddNewWorkoutView;
import com.example.workout.presenter.AddNewWorkoutPresenter;
import com.example.workout.util.Util;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddNewWorkoutFragment extends Fragment implements IAddNewWorkoutView {

    private EditText workoutNameEditText, burnedCaloriesEditText, durationEditText;
    private TextView dateOfWorkoutTextView;
    private ImageButton calendarImageButton;
    private Button addButton;
    private IAddNewWorkoutPresenter addNewWorkoutPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_workout_fragment, container, false);
        this.initializeElements(view);
        this.setOnClickListeners();
        return view;
    }

    private void initializeElements(View view) {
        this.addButton = view.findViewById(R.id.add_new_workout_button);
        this.burnedCaloriesEditText = view.findViewById(R.id.add_workout_calories_editText);
        this.calendarImageButton = view.findViewById(R.id.add_workout_choose_date_imageButton);
        this.durationEditText = view.findViewById(R.id.add_workout_duration_editText);
        this.dateOfWorkoutTextView = view.findViewById(R.id.add_workout_date_of_workout_textView);
        this.workoutNameEditText = view.findViewById(R.id.add_workout_name_editText);
        this.addNewWorkoutPresenter = new AddNewWorkoutPresenter(this);
    }

    private void setOnClickListeners() {
        this.calendarImageButton.setOnClickListener(v -> this.pickDate());

        this.addButton.setOnClickListener(v -> addNewWorkoutPresenter.handleAddNewWorkout(
                workoutNameEditText.getText().toString(),
                burnedCaloriesEditText.getText().toString(),
                dateOfWorkoutTextView.getText().toString(),
                durationEditText.getText().toString()));
    }

    private void pickDate() {
        Calendar mCalender = Calendar.getInstance();
        int year = mCalender.get(Calendar.YEAR);
        int month = mCalender.get(Calendar.MONTH);
        int dayOfMonth = mCalender.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year1, month1, dayOfMonth1) -> {
            String date = dayOfMonth1 + "-" + (month1 + 1) + "-" + year1;
            dateOfWorkoutTextView.setText(date);
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    @Override
    public void updateUI(String text) {

    }

    @Override
    public void informUserError(int msgId) {
        Util.makeSnackBar(getView(), msgId, Snackbar.LENGTH_SHORT, R.color.red);
    }
}

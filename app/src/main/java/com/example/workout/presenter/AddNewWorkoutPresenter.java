package com.example.workout.presenter;

import android.text.TextUtils;

import com.example.workout.R;
import com.example.workout.interfaces.IAddNewWorkoutPresenter;
import com.example.workout.interfaces.IAddNewWorkoutView;
import com.example.workout.util.GlobalValues;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddNewWorkoutPresenter implements IAddNewWorkoutPresenter {

    private final IAddNewWorkoutView addNewWorkoutView;

    public AddNewWorkoutPresenter(IAddNewWorkoutView addNewWorkoutView) {
        this.addNewWorkoutView = addNewWorkoutView;
    }

    @Override
    public void handleAddNewWorkout(String workoutName, String calories, String dateOfWorkout, String duration) {
        if (TextUtils.isEmpty(workoutName)) {
            workoutName = setEmptyWorkoutName();
        }

        if (TextUtils.isEmpty(calories)) {
            this.addNewWorkoutView.informUserError(R.string.enter_burned_calories);
            return;
        }

        if (TextUtils.isEmpty(duration)) {
            this.addNewWorkoutView.informUserError(R.string.enter_duration_of_workout);
            return;
        }

        if (!dateOfWorkout.matches(GlobalValues.DATE_PATTERN_REGEX)){
            this.addNewWorkoutView.informUserError(R.string.choose_date);
            return;
        }

    }

    private String setEmptyWorkoutName() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(GlobalValues.DATE_PATTERN);
        LocalDateTime now = LocalDateTime.now();
        return GlobalValues.WORKOUT_NAME.concat(dateTimeFormatter.format(now));
    }
}

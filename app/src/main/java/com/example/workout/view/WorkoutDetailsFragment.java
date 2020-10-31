package com.example.workout.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.workout.R;
import com.example.workout.model.Workout;

import java.util.Objects;

public class WorkoutDetailsFragment extends Fragment {

    private TextView nameTextView, caloriesTextView, durationTextView, dateOfWorkoutTextView, addDateTextView;
    private ImageView workoutImageView;
    private Workout workout;

    public WorkoutDetailsFragment(Workout workout) {
        this.workout = workout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_histroy_details_fragment, container, false);

        this.initializeElements(view);
        this.setElements();

        return view;
    }

    private void initializeElements(View view) {
        this.addDateTextView = view.findViewById(R.id.details_add_date_textView);
        this.dateOfWorkoutTextView = view.findViewById(R.id.details_date_of_workout_textView);
        this.caloriesTextView = view.findViewById(R.id.details_burned_calories_textView);
        this.durationTextView = view.findViewById(R.id.details_duration_textView);
        this.nameTextView = view.findViewById(R.id.details_workout_name_textView);
        this.workoutImageView = view.findViewById(R.id.details_image_imageView);
    }

    private void setElements() {
        this.addDateTextView.setText(this.workout.getAddedDate());
        this.dateOfWorkoutTextView.setText(this.workout.getDateOfWorkout());
        this.caloriesTextView.setText(this.workout.getBurnedCalories());
        this.durationTextView.setText(String.valueOf(this.workout.getMinutes()));
        this.nameTextView.setText(this.workout.getWorkoutName());
        Glide.with(Objects.requireNonNull(getContext()))
                .load(workout.getPhotoUri())
                .placeholder(R.mipmap.ic_launcher)
                .into(workoutImageView);
    }


}

package com.example.workout.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.workout.R;
import com.example.workout.interfaces.IAddNewWorkoutPresenter;
import com.example.workout.interfaces.IAddNewWorkoutView;
import com.example.workout.presenter.AddNewWorkoutPresenter;
import com.example.workout.util.FragmentNavigation;
import com.example.workout.util.GlobalValues;
import com.example.workout.util.Util;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddNewWorkoutFragment extends Fragment implements IAddNewWorkoutView {

    public static final String TAG = AddNewWorkoutFragment.class.getSimpleName();

    private EditText workoutNameEditText, burnedCaloriesEditText, durationEditText;
    private TextView dateOfWorkoutTextView, photoTextView;
    private ImageButton calendarImageButton, uploadPhotoImageButton, takePhotoImageButton;
    private Button addButton;
    private ImageView selectedImageView;
    private IAddNewWorkoutPresenter addNewWorkoutPresenter;
    private ProgressBar progressBar;

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
        this.uploadPhotoImageButton = view.findViewById(R.id.add_workout_upload_photo_imageButton);
        this.takePhotoImageButton = view.findViewById(R.id.add_workout_take_photo_imageButton);
        this.selectedImageView = view.findViewById(R.id.uploaded_image_imageView);
        this.photoTextView = view.findViewById(R.id.add_workout_upload_photo_imageButton_textView);
        this.progressBar = view.findViewById(R.id.progressBar);
        this.addNewWorkoutPresenter = new AddNewWorkoutPresenter(this);
    }

    private void setOnClickListeners() {
        this.calendarImageButton.setOnClickListener(v -> this.pickDate());

        this.addButton.setOnClickListener(v -> {
            this.progressBar.setVisibility(View.VISIBLE);

            addNewWorkoutPresenter.handleAddNewWorkout(
                    workoutNameEditText.getText().toString(),
                    burnedCaloriesEditText.getText().toString(),
                    dateOfWorkoutTextView.getText().toString(),
                    durationEditText.getText().toString(),
                    selectedImageView.getContentDescription().toString());
        });

        this.uploadPhotoImageButton.setOnClickListener(v -> this.pickImage());
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(GlobalValues.STAR);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, GlobalValues.MIME_TYPES);
        startActivityForResult(intent, GlobalValues.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.progressBar.setVisibility(View.VISIBLE);
        this.addNewWorkoutPresenter.handlePickPhoto(requestCode, resultCode, data);
    }

    private void pickDate() {
        Calendar mCalender = Calendar.getInstance();
        int year = mCalender.get(Calendar.YEAR);
        int month = mCalender.get(Calendar.MONTH);
        int dayOfMonth = mCalender.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme,
                (view, year1, month1, dayOfMonth1) -> {
                    String date = dayOfMonth1 + GlobalValues.LINE + (month1 + 1) + GlobalValues.LINE + year1;
                    dateOfWorkoutTextView.setText(date);
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    @Override
    public void updateUI(Uri imageUri) {
        this.progressBar.setVisibility(View.INVISIBLE);
        this.photoTextView.setVisibility(View.GONE);
        this.selectedImageView.setImageURI(imageUri);
        this.selectedImageView.setVisibility(View.VISIBLE);
        this.selectedImageView.setContentDescription(imageUri.toString());
        Util.makeSnackBar(getView(), R.string.image_upload_successfully, Snackbar.LENGTH_SHORT, R.color.green);
    }

    @Override
    public void success() {
        this.progressBar.setVisibility(View.INVISIBLE);
        Util.makeSnackBar(getView(), R.string.data_added_successfully, Snackbar.LENGTH_SHORT, R.color.green);
        FragmentNavigation.getInstance(getContext()).popBackStack();
    }

    @Override
    public void informUserError(int msgId) {
        this.progressBar.setVisibility(View.INVISIBLE);
        Util.makeSnackBar(getView(), msgId, Snackbar.LENGTH_SHORT, R.color.red);
    }
}

package com.example.workout.presenter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.example.workout.R;
import com.example.workout.database.FirebaseDb;
import com.example.workout.interfaces.IAddNewWorkoutPresenter;
import com.example.workout.interfaces.IAddNewWorkoutView;
import com.example.workout.model.Workout;
import com.example.workout.util.GlobalValues;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddNewWorkoutPresenter implements IAddNewWorkoutPresenter {

    private final IAddNewWorkoutView addNewWorkoutView;

    public AddNewWorkoutPresenter(IAddNewWorkoutView addNewWorkoutView) {
        this.addNewWorkoutView = addNewWorkoutView;
    }

    @Override
    public void handleAddNewWorkout(String workoutName, String calories, String dateOfWorkout, String duration, String imageUri) {
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

        if (!dateOfWorkout.matches(GlobalValues.DATE_PATTERN_REGEX)) {
            this.addNewWorkoutView.informUserError(R.string.choose_date);
            return;
        }

        Workout workout = new Workout(workoutName, calories, dateOfWorkout, Integer.parseInt(duration), getCurrentLocalDateTime(GlobalValues.DATE_PATTERN_ADD), imageUri);

        FirebaseDb.getInstance().insertNewWorkout(workout, this);
    }

    @Override
    public void failure(int msgId) {
        this.addNewWorkoutView.informUserError(msgId);
    }

    @Override
    public void success() {
        this.addNewWorkoutView.success();
    }

    @Override
    public void handlePhoto(int requestCode, int resultCode, Intent data, Context context) {
        if (requestCode == GlobalValues.REQUEST_CODE_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, filePathColumn, null, null, null);
            if (cursor == null) {
                this.addNewWorkoutView.informUserError(R.string.fail);
                return;
            }

            cursor.moveToLast();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            this.addNewWorkoutView.updateUI(Uri.fromFile(new File(filePath)));
        } else if (requestCode == GlobalValues.REQUEST_CODE_PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            this.addNewWorkoutView.updateUI(data.getData());
        } else {
            this.addNewWorkoutView.informUserError(R.string.fail_image_choose);
        }
    }

    @Override
    public void setTakePhotoIntent(Intent intent, Context context) {
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, GlobalValues.NEW_PICTURE);
            values.put(MediaStore.Images.Media.DESCRIPTION, GlobalValues.NEW_PICTURE);
            Uri imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            this.addNewWorkoutView.startTakePhoto(intent);
        } else {
            this.addNewWorkoutView.informUserError(R.string.fail);
        }
    }

    private String setEmptyWorkoutName() {
        return GlobalValues.WORKOUT_NAME.concat(getCurrentLocalDateTime(GlobalValues.DATE_PATTERN));
    }

    private String getCurrentLocalDateTime(String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }
}

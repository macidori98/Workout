package com.example.workout.util;

import android.Manifest;

public class GlobalValues {
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int USERNAME_MIN_LENGTH = 4;
    public static final int SPLASHSCREEN_LENGTH = 1500;
    public static final int MAX_RANDOM_VALUE = 10000;
    public static final int STORAGE_PERMISSION_CODE = 1;
    public static final int REQUEST_CODE = 1;

    public static final String USERS = "users";
    public static final String WORKOUT = "workout";

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String WORKOUT_NAME_ = "workoutName";
    public static final String BURNED_CALORIES = "burnedCalories";
    public static final String DATE_OF_WORKOUT = "dateOfWorkout";
    public static final String MINUTES = "minutes";
    public static final String ADDED_DATE = "addedDate";
    public static final String PHOTO_URI = "photoUri";

    public static final String DB = "DB";
    public static final String IMAGE = "images/";
    public static final String DATE_PATTERN = "yyyyMMddHHmmss";
    public static final String DATE_PATTERN_ADD = "dd-MM-yyyy HH:mm";
    public static final String WORKOUT_NAME = "workout_";
    public static final String DATE_PATTERN_REGEX = "\\d{1,2}-\\d{1,2}-\\d{4}";
    public static final String REGEX = "[a-zA-Z0-9]*";
    public static final String LINE = "-";
    public static final String STAR = "image/*";
    public static final String EMPTY = "";
    public static final String[] MIME_TYPES = {
            "image/jpeg",
            "image/jpg",
            "image/png"
    };

    public static final String[] PERMISSIONS = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    public static final String HELLO = "Hello, ";


    public static String CURRENT_SESSION;
    public static int CHECK = 0;

}
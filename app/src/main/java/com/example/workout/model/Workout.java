package com.example.workout.model;

public class Workout {
    private final String workoutName;
    private final String burnedCalories;
    private final String dateOfWorkout;
    private final int minutes;
    private final String addedDate;

    public Workout(String workoutName, String burnedCalories, String dateOfWorkout, int minutes, String addedDate) {
        this.workoutName = workoutName;
        this.burnedCalories = burnedCalories;
        this.dateOfWorkout = dateOfWorkout;
        this.minutes = minutes;
        this.addedDate = addedDate;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public String getBurnedCalories() {
        return burnedCalories;
    }

    public String getDateOfWorkout() {
        return dateOfWorkout;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getAddedDate() {
        return addedDate;
    }
}

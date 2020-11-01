package com.example.workout.database;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.workout.R;
import com.example.workout.interfaces.IAddNewWorkoutPresenter;
import com.example.workout.interfaces.IHomePresenter;
import com.example.workout.interfaces.ILoginPresenter;
import com.example.workout.interfaces.ISignUpPresenter;
import com.example.workout.model.User;
import com.example.workout.model.Workout;
import com.example.workout.util.GlobalValues;
import com.example.workout.util.Util;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static com.example.workout.util.Util.setSharedPref;

public class FirebaseDb {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static DatabaseReference databaseReference;
    private static FirebaseUser firebaseUser;
    private static FirebaseDb databaseInstance;

    public static FirebaseDb getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new FirebaseDb();
        }

        return databaseInstance;
    }

    public void login(String email, String password, ILoginPresenter loginPresenter, Context context) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseUser = Objects.requireNonNull(task.getResult()).getUser();
                this.setCurrentSession(loginPresenter, context);
            } else {
                loginPresenter.failure(R.string.login_fail);
            }
        });
    }

    public void createUser(String email, String password, String username, ISignUpPresenter signUpPresenter, Context context) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseUser = firebaseAuth.getCurrentUser();
                this.insertUser(email, username, signUpPresenter, context);
            } else {
                signUpPresenter.failure(R.string.user_already_exists);
            }
        });
    }

    public void insertNewWorkout(Workout workout, IAddNewWorkoutPresenter addNewWorkoutPresenter) {
        if (TextUtils.isEmpty(workout.getPhotoUri())) {
            insertWorkout(workout, addNewWorkoutPresenter);
        } else {
            uploadPhoto(workout, addNewWorkoutPresenter);
        }
    }

    public void getWorkoutHistory(IHomePresenter homePresenter) {
        databaseReference = database.getReference(GlobalValues.WORKOUT).child(GlobalValues.CURRENT_SESSION);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Workout> workoutList = new ArrayList<>();

                if (snapshot.hasChildren()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        String workoutName = Objects.requireNonNull(snap.child(GlobalValues.WORKOUT_NAME_).getValue()).toString();
                        String burnedCalories = Objects.requireNonNull(snap.child(GlobalValues.BURNED_CALORIES).getValue()).toString();
                        String dateOfWorkout = Objects.requireNonNull(snap.child(GlobalValues.DATE_OF_WORKOUT).getValue()).toString();
                        int minutes = Integer.parseInt(Objects.requireNonNull(snap.child(GlobalValues.MINUTES).getValue()).toString());
                        String addedDate = Objects.requireNonNull(snap.child(GlobalValues.ADDED_DATE).getValue()).toString();
                        String photoUri = Objects.requireNonNull(snap.child(GlobalValues.PHOTO_URI).getValue()).toString();
                        workoutList.add(new Workout(workoutName, burnedCalories, dateOfWorkout, minutes, addedDate, photoUri));
                    }
                }

                homePresenter.sendDataList(workoutList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(GlobalValues.DB, GlobalValues.DB);
            }
        });
    }

    public void logout(IHomePresenter homePresenter) {
        firebaseAuth.signOut();
        homePresenter.logoutSuccess();
    }

    private void insertWorkout(Workout workout, IAddNewWorkoutPresenter addNewWorkoutPresenter) {
        databaseReference = database.getReference(GlobalValues.WORKOUT);
        String id = databaseReference.push().getKey();
        databaseReference.child(GlobalValues.CURRENT_SESSION).child(Objects.requireNonNull(id)).setValue(workout).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                addNewWorkoutPresenter.success();
            } else {
                addNewWorkoutPresenter.failure(R.string.data_adding_fail);
            }
        });
    }

    private void uploadPhoto(Workout workout, IAddNewWorkoutPresenter addNewWorkoutPresenter) {
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference().child(GlobalValues.IMAGE + UUID.randomUUID().toString());
        Uri uri = Uri.parse(workout.getPhotoUri());
        mStorageRef.putFile(uri).addOnCompleteListener(task -> {
            Task<Uri> result = Objects.requireNonNull(task.getResult()).getStorage().getDownloadUrl();
            result.addOnCompleteListener(task1 -> {
                workout.setPhotoUri(Objects.requireNonNull(result.getResult()).toString());
                insertWorkout(workout, addNewWorkoutPresenter);
            });
        });
    }

    private void insertUser(String email, String username, ISignUpPresenter signUpPresenter, Context context) {
        databaseReference = database.getReference(GlobalValues.USERS);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(username)) {
                    String usernameNew = username.concat(String.valueOf(new Random().nextInt(GlobalValues.MAX_RANDOM_VALUE)));
                    insert(new User(email, usernameNew), signUpPresenter, context);
                } else {
                    insert(new User(email, username), signUpPresenter, context);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                signUpPresenter.failure(R.string.fail);
            }
        });
    }

    private void insert(User user, ISignUpPresenter signUpPresenter, Context context) {
        databaseReference.child(user.getUsername()).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Util.setSharedPref(context, user.getUsername());
                GlobalValues.CURRENT_SESSION = user.getUsername();
                signUpPresenter.success();
            }
        });
    }

    private void setCurrentSession(ILoginPresenter loginPresenter, Context context) {
        databaseReference = database.getReference(GlobalValues.USERS);
        String firebaseUserEmail = firebaseUser.getEmail();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String email = Objects.requireNonNull(snap.child(GlobalValues.EMAIL).getValue()).toString();
                    if (email.equals(firebaseUserEmail)) {
                        GlobalValues.CURRENT_SESSION = Objects.requireNonNull(snap.child(GlobalValues.USERNAME).getValue()).toString();
                        setSharedPref(context, GlobalValues.CURRENT_SESSION);
                        loginPresenter.success();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(GlobalValues.DB, error.toString());
            }
        });
    }
}
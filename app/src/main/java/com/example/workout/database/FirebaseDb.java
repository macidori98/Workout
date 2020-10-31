package com.example.workout.database;

import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.workout.R;
import com.example.workout.interfaces.IAddNewWorkoutPresenter;
import com.example.workout.interfaces.ILoginPresenter;
import com.example.workout.interfaces.ISignUpPresenter;
import com.example.workout.model.User;
import com.example.workout.model.Workout;
import com.example.workout.util.GlobalValues;
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

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

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

    public FirebaseUser getFirebaseUser() {
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            this.setCurrentSession();
        }
        return firebaseUser;
    }

    public void login(String email, String password, ILoginPresenter loginPresenter) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseUser = Objects.requireNonNull(task.getResult()).getUser();
                this.setCurrentSession();
                loginPresenter.loginSuccess();
            } else {
                loginPresenter.loginFail();
            }
        });
    }

    public void createUser(String email, String password, String username, ISignUpPresenter signUpPresenter) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                this.insertUser(email, username, signUpPresenter);
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

    private void insertUser(String email, String username, ISignUpPresenter signUpPresenter) {
        databaseReference = database.getReference(GlobalValues.USERS);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(username)) {
                    String usernameNew = username.concat(String.valueOf(new Random().nextInt(GlobalValues.MAX_RANDOM_VALUE)));
                    insert(new User(email, usernameNew), signUpPresenter);
                } else {
                    insert(new User(email, username), signUpPresenter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                signUpPresenter.failure(R.string.fail);
            }
        });
    }

    private void insert(User user, ISignUpPresenter signUpPresenter) {
        databaseReference.child(user.getUsername()).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                signUpPresenter.success();
            }
        });
    }

    private void setCurrentSession() {
        databaseReference = database.getReference(GlobalValues.USERS);
        String firebaseUserEmail = firebaseUser.getEmail();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String email = Objects.requireNonNull(snap.child(GlobalValues.EMAIL).getValue()).toString();
                    if (email.equals(firebaseUserEmail)) {
                        GlobalValues.CURRENT_SESSION = Objects.requireNonNull(snap.child(GlobalValues.USERNAME).getValue()).toString();
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

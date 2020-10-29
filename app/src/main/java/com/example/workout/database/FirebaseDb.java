package com.example.workout.database;

import androidx.annotation.NonNull;

import com.example.workout.R;
import com.example.workout.interfaces.ILoginPresenter;
import com.example.workout.interfaces.ISignUpPresenter;
import com.example.workout.model.User;
import com.example.workout.util.GlobalValues;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Random;

public class FirebaseDb {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference databaseReference;
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
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
        return firebaseUser;
    }

    public void login(String email, String password, ILoginPresenter loginPresenter) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseUser = Objects.requireNonNull(task.getResult()).getUser();
                loginPresenter.loginSuccess();
            } else {
                loginPresenter.loginFail();
            }
        });
    }

    public void createUser(String email, String password, String username, ISignUpPresenter signUpPresenter) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                insertUser(email, username, signUpPresenter);
            } else {
                signUpPresenter.failure(R.string.user_already_exists);
            }
        });
    }

    //TODO ne legyen ugyan az a username
    private void insertUser(String email, String username, ISignUpPresenter signUpPresenter) {
        databaseReference = database.getReference(GlobalValues.USERS);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(username)) {
                    String usernameNew = username.concat(String.valueOf(new Random().nextInt(100)));
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
}

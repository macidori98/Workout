package com.example.workout.database;

import com.example.workout.R;
import com.example.workout.interfaces.ILoginPresenter;
import com.example.workout.interfaces.ISignUpPresenter;
import com.example.workout.model.User;
import com.example.workout.util.GlobalValues;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class FirebaseDb {
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser firebaseUser;
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference databaseReference;

    private static FirebaseDb databaseInstance;

    public static FirebaseDb getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new FirebaseDb();
        }

        return databaseInstance;
    }

    public void login(String email, String password, ILoginPresenter iLoginPresenter) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseUser = Objects.requireNonNull(task.getResult()).getUser();
                iLoginPresenter.loginSuccess();
            } else {
                iLoginPresenter.loginFail();
            }
        });
    }

    public void createUser(String email, String password, String username, ISignUpPresenter iSignUpPresenter) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                insertUser(email, username, iSignUpPresenter);
            } else {
                iSignUpPresenter.failure(R.string.user_already_exists);
            }
        });
    }

    //TODO ne legyen ugyan az a username
    private void insertUser(String email, String username, ISignUpPresenter iSignUpPresenter) {
        databaseReference = database.getReference(GlobalValues.USERS);
        User user = new User(email, username);
        databaseReference.child(username).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                iSignUpPresenter.success();
            }
        });
    }
}

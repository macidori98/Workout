package com.example.workout.database;

import com.example.workout.R;
import com.example.workout.interfaces.ILogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class DatabaseHelper {
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser firebaseUser;

    private static DatabaseHelper databaseInstance;

    public static DatabaseHelper getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new DatabaseHelper();
        }

        return databaseInstance;
    }

    public void login(String email, String password, ILogin iLogin) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                firebaseUser = Objects.requireNonNull(task.getResult()).getUser();
                iLogin.loginOnSuccess();
            } else {
                iLogin.informUserError(R.string.login_fail, R.color.red);
            }
        });
    }
}

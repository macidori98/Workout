package com.example.workout.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.workout.R;
import com.example.workout.database.DatabaseUtils;
import com.example.workout.utils.Util;
import com.google.android.material.snackbar.Snackbar;


public class LoginFragment extends Fragment {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        this.initializeElements(view);

        this.loginButton.setOnClickListener(v -> login());
        return view;
    }

    private void initializeElements(View view) {
        this.emailEditText = view.findViewById(R.id.login_email_editText);
        this.passwordEditText = view.findViewById(R.id.login_password_editText);
        this.loginButton = view.findViewById(R.id.login_login_button);
    }

    private void login() {
        String email = this.emailEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();

        if (isValidEmail(email) && isValidPassword(password)) {
            DatabaseUtils.firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DatabaseUtils.firebaseUser = task.getResult().getUser();
                    Util.makeSnackBar(getView(),R.string.login_successful , Snackbar.LENGTH_SHORT, R.color.green);
                } else {
                    Util.makeSnackBar(getView(),R.string.check_email_and_password , Snackbar.LENGTH_SHORT, R.color.red);
                }
            });
        } else {
            Util.makeSnackBar(getView(),R.string.check_email_and_password , Snackbar.LENGTH_SHORT, R.color.red);
        }
    }

    private boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean isValidPassword(String target) {
        return (!TextUtils.isEmpty(target) && (target.length() >= 6));
    }
}

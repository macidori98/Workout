package com.example.workout.presenter;

import android.widget.CheckBox;

import com.example.workout.R;
import com.example.workout.database.FirebaseDb;
import com.example.workout.interfaces.ISignUpPresenter;
import com.example.workout.interfaces.ISignUpView;
import com.example.workout.util.GlobalValues;
import com.example.workout.util.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPresenter implements ISignUpPresenter {
    private final ISignUpView signUpView;

    public SignUpPresenter(ISignUpView signUpView) {
        this.signUpView = signUpView;
    }

    @Override
    public void handleSignUp(String email, String username, String password, String confirmPassword, CheckBox terms) {
        if (!terms.isChecked()) {
            signUpView.informUserError(R.string.check_terms);
            return;
        }

        if (!this.passwordsMatch(password, confirmPassword)) {
            signUpView.informUserError(R.string.passwords_dont_match);
            return;
        }

        if (!Util.isValidStringLength(password, GlobalValues.USERNAME_MIN_LENGTH)) {
            signUpView.informUserError(R.string.password_length);
            return;
        }

        if (!Util.isValidEmail(email)) {
            signUpView.informUserError(R.string.invalid_email);
            return;
        }

        if (!Util.isValidStringLength(username, GlobalValues.USERNAME_MIN_LENGTH)) {
            signUpView.informUserError(R.string.username_length);
            return;
        }

        if (!this.correctUsername(username)) {
            signUpView.informUserError(R.string.username_fail);
            return;
        }

        FirebaseDb.getInstance().createUser(email, password, username, this);
    }

    private boolean correctUsername(String username) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    @Override
    public void failure(int msgId) {
        signUpView.informUserError(msgId);
    }

    @Override
    public void success() {
        signUpView.signUpSuccess();
    }

    private boolean passwordsMatch(String password, String confirmPassword) {
        return ((password.length() == confirmPassword.length()) &&
                (password.equals(confirmPassword)));
    }


}

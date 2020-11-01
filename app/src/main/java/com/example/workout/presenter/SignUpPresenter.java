package com.example.workout.presenter;

import android.content.Context;
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
    public void handleSignUp(String email, String username, String password, String confirmPassword, CheckBox terms, Context context) {
        if (!terms.isChecked()) {
            this.signUpView.informUserError(R.string.check_terms);
            return;
        }

        if (!this.passwordsMatch(password, confirmPassword)) {
            this.signUpView.informUserError(R.string.passwords_dont_match);
            return;
        }

        if (!Util.isValidStringLength(password, GlobalValues.USERNAME_MIN_LENGTH)) {
            this.signUpView.informUserError(R.string.password_length);
            return;
        }

        if (!Util.isValidEmail(email)) {
            this.signUpView.informUserError(R.string.invalid_email);
            return;
        }

        if (!Util.isValidStringLength(username, GlobalValues.USERNAME_MIN_LENGTH)) {
            this.signUpView.informUserError(R.string.username_length);
            return;
        }

        if (!this.correctUsername(username)) {
            this.signUpView.informUserError(R.string.username_fail);
            return;
        }

        FirebaseDb.getInstance().createUser(email, password, username, this, context);
    }

    @Override
    public void failure(int msgId) {
        this.signUpView.informUserError(msgId);
    }

    @Override
    public void success() {
        this.signUpView.signUpSuccess();
    }

    private boolean correctUsername(String username) {
        Pattern pattern = Pattern.compile(GlobalValues.REGEX);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private boolean passwordsMatch(String password, String confirmPassword) {
        return ((password.length() == confirmPassword.length()) &&
                (password.equals(confirmPassword)));
    }


}

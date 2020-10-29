package com.example.workout.interfaces;

import android.widget.CheckBox;

public interface ISignUpPresenter {
    void handleSignUp(String email, String username, String password, String confirmPassword, CheckBox terms);

    void failure(int msgId);

    void success();
}

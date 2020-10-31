package com.example.workout.interfaces;

import android.content.Context;
import android.widget.CheckBox;

public interface ISignUpPresenter {
    void handleSignUp(String email, String username, String password, String confirmPassword, CheckBox terms, Context context);

    void failure(int msgId);

    void success();
}

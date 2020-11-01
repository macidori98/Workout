package com.example.workout.interfaces;

import android.content.Context;
import android.widget.CheckBox;

public interface ISignUpPresenter extends IBasePresenter {
    void handleSignUp(String email, String username, String password, String confirmPassword, CheckBox terms, Context context);
}

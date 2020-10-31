package com.example.workout.interfaces;

import android.content.Context;

public interface ILoginPresenter {
    void handleLogin(String email, String password, Context context);

    void loginSuccess();

    void loginFail();
}

package com.example.workout.interfaces;

import android.content.Context;

public interface ILoginPresenter extends IBasePresenter {
    void handleLogin(String email, String password, Context context);
}

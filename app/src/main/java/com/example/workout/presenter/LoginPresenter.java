package com.example.workout.presenter;

import android.content.Context;

import com.example.workout.R;
import com.example.workout.database.FirebaseDb;
import com.example.workout.interfaces.ILoginPresenter;
import com.example.workout.interfaces.ILoginView;
import com.example.workout.util.GlobalValues;
import com.example.workout.util.Util;

public class LoginPresenter implements ILoginPresenter {

    private final ILoginView loginView;

    public LoginPresenter(ILoginView iLogin) {
        this.loginView = iLogin;
    }

    @Override
    public void handleLogin(String email, String password, Context context) {
        if (Util.isValidEmail(email) && Util.isValidStringLength(password, GlobalValues.PASSWORD_MIN_LENGTH)) {
            FirebaseDb.getInstance().login(email, password, this, context);
        } else {
            this.loginView.informUserError(R.string.check_email_and_password);
        }
    }

    @Override
    public void loginSuccess() {
        this.loginView.loginOnSuccess();
    }

    @Override
    public void loginFail() {
        this.loginView.informUserError(R.string.login_fail);
    }
}

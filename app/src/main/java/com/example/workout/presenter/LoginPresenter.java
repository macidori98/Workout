package com.example.workout.presenter;

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
    public void handleLogin(String email, String password) {
        if (Util.isValidEmail(email) && Util.isValidStringLength(password, GlobalValues.PASSWORD_MIN_LENGTH)) {
            FirebaseDb.getInstance().login(email, password, this);
        } else {
            loginView.informUserError(R.string.check_email_and_password);
        }
    }

    @Override
    public void loginSuccess() {
        loginView.loginOnSuccess();
    }

    @Override
    public void loginFail() {
        loginView.informUserError(R.string.login_fail);
    }
}

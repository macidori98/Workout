package com.example.workout.presenters;

import com.example.workout.R;
import com.example.workout.database.DatabaseHelper;
import com.example.workout.interfaces.ILoginView;
import com.example.workout.interfaces.ILoginPresenter;
import com.example.workout.utils.GlobalValues;
import com.example.workout.utils.Util;

public class LoginPresenter implements ILoginPresenter {

    private final ILoginView iLoginView;

    public LoginPresenter(ILoginView iLogin) {
        this.iLoginView = iLogin;
    }

    @Override
    public void handleLogin(String email, String password) {
        if (Util.isValidEmail(email) && Util.isValidStringLength(password, GlobalValues.PASSWORD_MIN_LENGTH)) {
            DatabaseHelper.getInstance().login(email, password, this);
        } else {
            iLoginView.informUserError(R.string.check_email_and_password);
        }
    }

    @Override
    public void loginSuccess() {
        iLoginView.loginOnSuccess();
    }

    @Override
    public void loginFail() {
        iLoginView.informUserError(R.string.login_fail);
    }
}

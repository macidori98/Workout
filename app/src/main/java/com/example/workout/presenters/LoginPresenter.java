package com.example.workout.presenters;

import com.example.workout.R;
import com.example.workout.database.DatabaseHelper;
import com.example.workout.interfaces.ILogin;
import com.example.workout.interfaces.ILoginPresenter;
import com.example.workout.utils.Util;

public class LoginPresenter implements ILoginPresenter {

    private final ILogin iLogin;

    public LoginPresenter(ILogin iLogin) {
        this.iLogin = iLogin;
    }

    @Override
    public void handleLogin(String email, String password) {
        if (Util.isValidEmail(email) && Util.isValidPassword(password)) {
            DatabaseHelper.getInstance().login(email, password, iLogin);
        } else {
            iLogin.informUserError(R.string.check_email_and_password, R.color.red);
        }
    }
}

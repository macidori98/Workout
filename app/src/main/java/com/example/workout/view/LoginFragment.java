package com.example.workout.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.workout.R;
import com.example.workout.interfaces.ILoginPresenter;
import com.example.workout.interfaces.ILoginView;
import com.example.workout.presenter.LoginPresenter;
import com.example.workout.util.FragmentNavigation;
import com.example.workout.util.Util;
import com.google.android.material.snackbar.Snackbar;


public class LoginFragment extends Fragment implements ILoginView {

    public static final String TAG = LoginFragment.class.getSimpleName();


    private EditText emailEditText, passwordEditText;
    private Button loginButton, signUpButton;
    private ILoginPresenter loginPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        this.initializeElements(view);
        this.setOnClickListeners();

        return view;
    }

    private void initializeElements(View view) {
        this.emailEditText = view.findViewById(R.id.login_email_editText);
        this.passwordEditText = view.findViewById(R.id.login_password_editText);
        this.loginButton = view.findViewById(R.id.login_login_button);
        this.signUpButton = view.findViewById(R.id.login_signup_button);

        this.loginPresenter = new LoginPresenter(this);
    }

    private void setOnClickListeners() {
        this.loginButton.setOnClickListener(v -> login());
        this.signUpButton.setOnClickListener(
                v -> FragmentNavigation.getInstance(getContext()).replaceFragment(new SignUpFragment(), R.id.fragment_content));
    }

    private void login() {
        this.loginPresenter.handleLogin(this.emailEditText.getText().toString(),
                this.passwordEditText.getText().toString());
    }

    @Override
    public void loginOnSuccess() {
        FragmentNavigation.getInstance(getContext()).replaceFragment(new HomeFragment(), R.id.fragment_content);
        Util.makeSnackBar(getView(), R.string.login_successful, Snackbar.LENGTH_SHORT, R.color.green);
    }

    @Override
    public void informUserError(int msgId) {
        Util.makeSnackBar(getView(), msgId, Snackbar.LENGTH_SHORT, R.color.red);
    }
}

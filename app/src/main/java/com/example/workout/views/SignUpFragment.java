package com.example.workout.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.workout.R;
import com.example.workout.interfaces.ISignUpPresenter;
import com.example.workout.interfaces.ISignUpView;
import com.example.workout.presenters.SignUpPresenter;
import com.example.workout.utils.FragmentNavigation;
import com.example.workout.utils.Util;
import com.google.android.material.snackbar.Snackbar;

public class SignUpFragment extends Fragment implements ISignUpView {

    private EditText emailEditText, usernameEditText, passwordEditText, confirmPasswordEditText;
    private CheckBox termsCheckBox;
    private Button signUpButton;
    private ISignUpPresenter iSignUpPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_fragment, container, false);
        initializeElements(view);
        setOnClickListeners();
        return view;
    }

    private void initializeElements(View view) {
        this.emailEditText = view.findViewById(R.id.signup_email_editText);
        this.confirmPasswordEditText = view.findViewById(R.id.signup_confirm_password_editText);
        this.passwordEditText = view.findViewById(R.id.signup_password_editText);
        this.usernameEditText = view.findViewById(R.id.signup_username_editText);
        this.termsCheckBox = view.findViewById(R.id.signup_checkbox);
        this.signUpButton = view.findViewById(R.id.signup_button);

        this.iSignUpPresenter = new SignUpPresenter(this);
    }

    private void setOnClickListeners() {
        this.signUpButton.setOnClickListener(v -> this.iSignUpPresenter.handleSignUp(
                this.emailEditText.getText().toString(),
                this.usernameEditText.getText().toString(),
                this.passwordEditText.getText().toString(),
                this.confirmPasswordEditText.getText().toString(),
                this.termsCheckBox
        ));
    }

    @Override
    public void informUserError(int msgId) {
        Util.makeSnackBar(getView(), msgId, Snackbar.LENGTH_SHORT, R.color.red);
    }


    @Override
    public void signUpSuccess() {
        FragmentNavigation.getInstance(getContext()).replaceFragment(new HomeFragment(), R.id.fragment_content);
        Util.makeSnackBar(getView(), R.string.signup_successful, Snackbar.LENGTH_SHORT, R.color.green);
    }
}

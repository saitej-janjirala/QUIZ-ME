package com.saitejajanjirala.quizme.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.saitejajanjirala.quizme.Helper;
import com.saitejajanjirala.quizme.R;
import com.saitejajanjirala.quizme.ui.activities.HomeActivity;


public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    private Button loginButton;
    private ProgressBar progressBar;
    private EditText email;
    private TextInputEditText password;
    private TextInputLayout passwordLayout;
    private ImageView swipeRightImage;
    private TextView swipeRightText;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);

        return view;
    }

    private void validateAndLogin(String em, String pwd) {
        boolean isEmailValid = Helper.validateEmail(em);
        boolean isPasswordValid = Helper.validatePassword(pwd);

        if(isEmailValid && isPasswordValid){
            login(em, pwd);
        }
        else{
            if(getContext() != null) {
                if(!isEmailValid){
                    email.setError(getString(R.string.enter_valid_email));
                }
                if(!isPasswordValid){
                    passwordLayout.setError(getString(R.string.pwd_error));
                }
                Toast.makeText(getContext().getApplicationContext(),getString( R.string.enter_correct_values), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void initViews(View view) {
        loginButton = view.findViewById(R.id.btn_login);
        progressBar = view.findViewById(R.id.progress_bar);
        email = view.findViewById(R.id.et_email);
        password = view.findViewById(R.id.et_password_text);
        passwordLayout = view.findViewById(R.id.et_password);
        swipeRightImage = view.findViewById(R.id.swipe_right_image);
        swipeRightText = view.findViewById(R.id.swipeRight);
        loginButton.setOnClickListener(v -> {
            String em = email.getText().toString();
            String pwd = password.getText()==null?"":password.getText().toString();
            validateAndLogin(em,pwd);
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(passwordLayout.isErrorEnabled()){
                    passwordLayout.setErrorEnabled(false);
                    passwordLayout.setError("");
                    passwordLayout.setErrorEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void login(String email,String password){
        Activity activity = getActivity();

        if(activity != null) {
            if(!Helper.hasInternetConnection(activity.getApplicationContext())){
                Toast.makeText(activity, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            openHomeScreen(activity);
                        } else {
                            onAuthenticationFailed(task);
                        }
                    });
        }
    }


    private void openHomeScreen(Activity activity) {
        startActivity(new Intent(activity, HomeActivity.class));
        activity.finish();
    }

    private void onAuthenticationFailed(Task<AuthResult> task) {
        if(getContext() != null && task.getException()!=null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
        }
    }

}
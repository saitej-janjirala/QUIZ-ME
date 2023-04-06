package com.saitejajanjirala.quizme.ui.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.saitejajanjirala.quizme.Helper;
import com.saitejajanjirala.quizme.R;


public class RegisterFragment extends Fragment {


    private ProgressBar progressBar;
    private Button register;
    private EditText etName,etEmail,etPassword,etRetypePassword;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        initViews(view);

        return view;
    }

    void initViews(View view){
        etName = view.findViewById(R.id.et_name);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etRetypePassword = view.findViewById(R.id.et_repassword);
        register = view.findViewById(R.id.btn_register);
        progressBar = view.findViewById(R.id.progress_bar);
        register.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String reTypePassword = etRetypePassword.getText().toString();
            validateAndRegister(name,email,password,reTypePassword);
        });
    }

    private void validateAndRegister(String name, String email, String password, String reTypePassword) {
        if (isFormValid(name, email, password, reTypePassword)) {
            registerUser(name, email, password);
        } else {
            handleErrors(name, email, password, reTypePassword);
            if (getContext() != null) {
                Toast.makeText(getContext().getApplicationContext(), getString(R.string.enter_correct_values), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isFormValid(String name, String email, String password, String reTypePassword) {
        return Helper.validateEmail(email) &&
                Helper.validateName(name) &&
                Helper.validatePassword(password) &&
                isRetypePasswordValid(password, reTypePassword);
    }

    private boolean isRetypePasswordValid(String password, String reTypePassword) {
        return password.equals(reTypePassword);
    }

    private void handleErrors(String name, String email, String password, String reTypePassword) {
        boolean isEmailValid = Helper.validateEmail(email);
        boolean isNameValid = Helper.validateName(name);
        boolean isPasswordValid = Helper.validatePassword(password);
        boolean isReTypePasswordValid = isRetypePasswordValid(password, reTypePassword);
        handleErrorMessages(isEmailValid,isNameValid,isPasswordValid,isReTypePasswordValid);
        // handle error messages
    }

    private void handleErrorMessages(boolean isEmailValid, boolean isNameValid, boolean isPasswordValid, boolean isReTypePasswordValid) {
        if(!isEmailValid){
            etEmail.setError(getString(R.string.enter_valid_email));
        }
        if(!isPasswordValid){
            etPassword.setError(getString(R.string.pwd_error));
        }
        if(!isNameValid){
            etName.setError(getString(R.string.name_error_msg));
        }
        if(!isReTypePasswordValid){
            etRetypePassword.setError(getString(R.string.both_passwords_same_error_msg));
        }
    }

    private void registerUser(String name, String email, String password) {
        Activity activity = getActivity();
        if(activity !=null){
            if(!Helper.hasInternetConnection(activity.getApplicationContext())){
                Toast.makeText(activity, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser user = authResult.getUser();
                        updateDisplayName(user,name);
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void updateDisplayName(FirebaseUser user, String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        if(user !=null) {
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);
                        resetForm();
                    });
        }
    }

    private void resetForm() {
        if(getContext() != null) {
            etEmail.setText(null);
            etPassword.setText(null);
            etName.setText(null);
            etPassword.setText(null);
            etRetypePassword.setText(null);
            Toast.makeText(getContext(), getString(R.string.account_creation_successful_msg), Toast.LENGTH_LONG).show();
        }
    }

}
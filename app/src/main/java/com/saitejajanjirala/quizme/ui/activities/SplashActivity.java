package com.saitejajanjirala.quizme.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.saitejajanjirala.quizme.Helper;
import com.saitejajanjirala.quizme.R;

public class SplashActivity extends AppCompatActivity {
    private LottieAnimationView animationView;
    private Button retryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        new Handler().postDelayed(this::checkIfUserIsLoggedIn,2000L);
    }

    private void initViews() {
        animationView= findViewById(R.id.lottie_animation_view);
        retryButton = findViewById(R.id.retry_button);
        retryButton.setOnClickListener(v -> {
            onClickRetry();
        });
    }

    private void onClickRetry() {
        retryButton.setVisibility(View.GONE);
        checkIfUserIsLoggedIn();
    }

    private void checkIfUserIsLoggedIn(){
        if(Helper.hasInternetConnection(getApplicationContext())){
            startAnimation();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if(auth.getCurrentUser() != null){
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                animationView.cancelAnimation();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        else{
            animationView.pauseAnimation();
            showNoInternetToast();
            showRetry();
        }
    }

    private void showNoInternetToast() {
        Toast.makeText(this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
    }

    private void showRetry() {
        retryButton.setVisibility(View.VISIBLE);
    }

    private void startAnimation(){
        animationView.playAnimation();
    }


}
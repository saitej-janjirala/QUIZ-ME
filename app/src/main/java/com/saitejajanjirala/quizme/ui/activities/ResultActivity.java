package com.saitejajanjirala.quizme.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.saitejajanjirala.quizme.R;
import com.saitejajanjirala.quizme.models.TestResult;

public class ResultActivity extends AppCompatActivity {

    private TextView score;
    private CircularProgressBar progressBar;
    private TestResult testResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setUpActionBar();
        getIntentData();
        initViews();
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        }
    }

    private void initViews() {
        progressBar = findViewById(R.id.circular_progress_bar);
        score = findViewById(R.id.score);
    }

    private void getIntentData() {
        try {
            testResult = (TestResult) getIntent().getSerializableExtra("result");
        }catch (Exception e){
            Log.e("quiz-app",e.getMessage());
            finish();
        }
    }

    private void setData() {
        score.setText(testResult.getScore()+" / 10");
        progressBar.setProgress(testResult.getScore());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
package com.saitejajanjirala.quizme.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.saitejajanjirala.quizme.R;
import com.saitejajanjirala.quizme.network.CATEGORIES;
import com.saitejajanjirala.quizme.network.DIFFICULTY;

public class QuizActivity extends AppCompatActivity {

    private CATEGORIES category;
    private DIFFICULTY difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getIntentData();
        initViews();
    }

    private void getIntentData(){
        category = (CATEGORIES) getIntent().getSerializableExtra("category");
        difficulty = (DIFFICULTY) getIntent().getSerializableExtra("difficulty");
    }
    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Call onBackPressed() when back arrow is clicked
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
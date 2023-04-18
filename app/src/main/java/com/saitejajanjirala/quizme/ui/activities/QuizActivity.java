package com.saitejajanjirala.quizme.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.saitejajanjirala.quizme.R;
import com.saitejajanjirala.quizme.models.Question;
import com.saitejajanjirala.quizme.network.CATEGORIES;
import com.saitejajanjirala.quizme.network.DIFFICULTY;
import com.saitejajanjirala.quizme.network.QuizApiHelper;
import com.saitejajanjirala.quizme.ui.adapter.QuestionsAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuizActivity extends AppCompatActivity {

    private CATEGORIES category;
    private DIFFICULTY difficulty;
    private ProgressBar progressBar;
    private Button retryButton;
    private List<Question> questionsList;
    private QuestionsAdapter questionsAdapter;
    private RecyclerView questionsRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getIntentData();
        initViews();
        setUpAdapter();
        fetchQuiz();
    }

    private void getIntentData(){
        category = (CATEGORIES) getIntent().getSerializableExtra("category");
        difficulty = (DIFFICULTY) getIntent().getSerializableExtra("difficulty");
    }
    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.progress_bar);
        retryButton = findViewById(R.id.retry_button);
        retryButton.setOnClickListener(view -> {
            fetchQuiz();
        });
        questionsRecyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Call onBackPressed() when back arrow is clicked
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchQuiz(){
        progressBar.setVisibility(View.VISIBLE);
        Disposable disposable = QuizApiHelper.getInstance()
                .getQuestions("",category.getName(),10,difficulty.getDifficulty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, throwable -> {
                    Log.i("quizme",throwable.getMessage());
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(QuizActivity.this, getString(R.string.unable_to_process_request_please_try_again_later), Toast.LENGTH_SHORT).show();
                    });
                });

    }

    private void setUpAdapter(){
        questionsList = new ArrayList<>();
        questionsAdapter = new QuestionsAdapter(this,questionsList);
        LinearLayoutManager ll = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        questionsRecyclerView.setLayoutManager(ll);
        questionsRecyclerView.setAdapter(questionsAdapter);
    }

    private void handleResponse(List<Question> questions) {
        progressBar.setVisibility(View.GONE);
        questionsList.clear();
        questionsList.addAll(questions);
        questionsAdapter.notifyDataSetChanged();
        Log.i("questions vamshi",questions.get(0).toString());
    }

}
package com.saitejajanjirala.quizme.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.saitejajanjirala.quizme.R;
import com.saitejajanjirala.quizme.models.Option;
import com.saitejajanjirala.quizme.models.Question;
import com.saitejajanjirala.quizme.models.QuestionCardData;
import com.saitejajanjirala.quizme.network.CATEGORIES;
import com.saitejajanjirala.quizme.network.DIFFICULTY;
import com.saitejajanjirala.quizme.network.QuizApiHelper;
import com.saitejajanjirala.quizme.ui.adapter.QuestionsAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuizActivity extends AppCompatActivity {

    private CATEGORIES category;
    private DIFFICULTY difficulty;
    private ProgressBar progressBar;
    private Button retryButton;
    private List<QuestionCardData> questionsList;
    private QuestionsAdapter questionsAdapter;
    private ViewPager2 questionsRecyclerView;
    private CardView questionsCardView;
    private TabLayout tabLayout;
    private Button finishButton;

    private TextView description;
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
            retryButton.setVisibility(View.GONE);
        });
        questionsRecyclerView = findViewById(R.id.recycler_view);
        questionsCardView= findViewById(R.id.questions_card);
        finishButton = findViewById(R.id.finish_test);
        finishButton.setOnClickListener(view -> {
            onFinishClicked();
        });
        tabLayout = findViewById(R.id.tab_layout);
        description = findViewById(R.id.description);
    }

    private void onFinishClicked() {
        List<QuestionCardData> list = questionsAdapter.getQuestionList();
        int pos = -1;
        for(int i=0;i<list.size();i++){
            if(!list.get(i).isAnswered()){
                pos = i;
                break;
            }
        }
        handleUnanswered(pos);
    }

    private void handleUnanswered(int pos) {
        if(pos != -1){
            Toast.makeText(this, "Please answer question "+(pos+1), Toast.LENGTH_SHORT).show();
            questionsRecyclerView.setCurrentItem(pos);
        }
        else{
            calculateResultAndNavigateToResultScreen();
        }
    }

    private void calculateResultAndNavigateToResultScreen() {

        int score =0;
        for(QuestionCardData i: questionsAdapter.getQuestionList()){
            List<Option> options = i.getOptions();
            List<Boolean> correctAnswers = i.getCorrectAnswers();
            if(i.isMultipleAnswers()){
                score+= handleMultipleAnswers(options,correctAnswers);
            }
            else{
                score+=handleSingleChoiceAnswers(options,correctAnswers);
            }
        }
    }

    private int handleSingleChoiceAnswers(List<Option> options, List<Boolean> correctAnswers){
        int ss = 0;
        for(int j=0;j<options.size();j++){
            if(options.get(j).isSelected() && correctAnswers.get(j)){
                ss = 1;
            }
        }
        return ss;
    }
    private int handleMultipleAnswers(List<Option> options, List<Boolean> correctAnswers) {
        int ss = 1;
        for(int j=0;j<options.size();j++){
            if((options.get(j).isSelected() && !correctAnswers.get(j))){
                return 0;
            }
        }
        return ss;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
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
                        questionsCardView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        description.setVisibility(View.GONE);
                        finishButton.setVisibility(View.GONE);
                        showRetry();
                        Toast.makeText(QuizActivity.this, getString(R.string.unable_to_process_request_please_try_again_later), Toast.LENGTH_SHORT).show();
                    });
                });

    }

    private void showRetry(){
        retryButton.setVisibility(View.VISIBLE);
    }
    private void setUpAdapter(){
        questionsList = new ArrayList<>();
        questionsAdapter = new QuestionsAdapter(this,questionsList);
        questionsRecyclerView.setAdapter(questionsAdapter);
        new TabLayoutMediator(tabLayout, questionsRecyclerView,
                (tab, position) -> {
                    tab.setText((position+1)+" / "+questionsList.size());
                }
        ).attach();
    }

    private void handleResponse(List<Question> questions) {
        progressBar.setVisibility(View.GONE);
        questionsCardView.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);
        finishButton.setVisibility(View.VISIBLE);
        setQuestionCards(questions);
        questionsAdapter.notifyDataSetChanged();
    }

    private void setQuestionCards(List<Question> questions){
        questionsList.clear();
        for(Question i : questions){
            questionsList.add(new QuestionCardData(i.getQuestion(),i.getOptions(),i.getCorrectAnswersList(),i.getDifficulty(),i.isMultipleCorrectAnswers()));
        }
    }

}
package com.saitejajanjirala.quizme.ui.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.saitejajanjirala.quizme.R;
import com.saitejajanjirala.quizme.models.TestResult;
import com.saitejajanjirala.quizme.network.CATEGORIES;

import java.util.List;

public class PastTestsAdapter extends RecyclerView.Adapter<PastTestsAdapter.PastTestsViewHolder> {
    private List<TestResult> testResults;
    private Context context;

    public PastTestsAdapter(List<TestResult> testResults, Context context) {
        this.testResults = testResults;
        this.context = context;
    }

    @NonNull
    @Override
    public PastTestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.past_test_item,parent,false);
        return new PastTestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastTestsViewHolder holder, int position) {
        if(position != RecyclerView.NO_POSITION){
            holder.bind(testResults.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return testResults.size();
    }

    class PastTestsViewHolder extends RecyclerView.ViewHolder{

        private ImageView difficultyIcon,categoryIcon;
        private TextView category,score,difficultyText;
        public PastTestsViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            difficultyIcon = itemView.findViewById(R.id.difficulty_icon);
            category = itemView.findViewById(R.id.category);
            score = itemView.findViewById(R.id.score);
            difficultyText = itemView.findViewById(R.id.difficulty_text);
        }

        void bind(TestResult testResult){
            categoryIcon.setImageResource(CATEGORIES.fromValue(testResult.getCategory()).getResId());
            score.setText(testResult.getScore()+" / 10");
            category.setText(testResult.getCategory());
            String difficulty = testResult.getDifficulty();
            int res = -1;
            int colRes = -1;
            if(difficulty.equals("easy")){
                res = R.string.easy;
                colRes = R.color.easy_color;
            }
            else if(difficulty.equals("medium")){
                res = R.string.medium;
                colRes = R.color.medium_color;
            }
            else{
                res = R.string.hard;
                colRes = R.color.hard_color;
            }
            difficultyText.setText(context.getString(res));
            difficultyIcon.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, colRes)));
        }
    }
}

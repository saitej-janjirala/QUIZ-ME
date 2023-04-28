package com.saitejajanjirala.quizme.ui.adapter;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitejajanjirala.quizme.R;
import com.saitejajanjirala.quizme.listeners.OnOptionSelectedListener;
import com.saitejajanjirala.quizme.models.Option;
import com.saitejajanjirala.quizme.models.Question;
import com.saitejajanjirala.quizme.models.QuestionCardData;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {
    private Context context;
    private List<QuestionCardData> questionList;

    public QuestionsAdapter(Context context, List<QuestionCardData> questionList){
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.questions_layout,parent,false);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {
        if(position != RecyclerView.NO_POSITION){
            holder.bind(position, questionList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }


    class QuestionsViewHolder extends RecyclerView.ViewHolder{

        TextView question;
        RecyclerView optionsRecyclerView;

        private OptionsAdapter optionsAdapter;
        public QuestionsViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question);
            optionsRecyclerView = itemView.findViewById(R.id.questions_sub_recycler_view);
        }

        void bind(int position,QuestionCardData data){
            question.setText((position+1)+"." +data.getQuestion());
            ArrayList<Option> answers = data.getOptions();
            OnOptionSelectedListener listener = (position1, isSelected, isMultiple) -> {
                if(!isMultiple){
                    for(int i=0;i<answers.size();i++){
                        Option x = answers.get(i);
                        x.setSelected(false);
                        answers.set(i,x);
                    }
                }
                Option x = answers.get(position1);
                x.setSelected(isSelected);
                answers.set(position1,x);
                optionsRecyclerView.post(() -> optionsAdapter.notifyDataSetChanged());
                data.setOptions(answers);
                questionList.set(position,data);
                notifyItemChanged(position);
            };



            optionsAdapter = new OptionsAdapter(context,answers, data.isMultipleAnswers(),listener);

            optionsRecyclerView.setAdapter(optionsAdapter);
        }
    }

    public List<QuestionCardData> getQuestionList() {
        return questionList;
    }
}

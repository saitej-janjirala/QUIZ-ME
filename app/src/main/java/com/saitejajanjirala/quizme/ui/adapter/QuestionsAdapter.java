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

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {
    private Context context;
    private List<Question> questionList;

    public QuestionsAdapter(Context context, List<Question> questionList){
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

        void bind(int position,Question data){
            question.setText((position+1)+"." +data.getQuestion());
            ArrayList<String> ans = new ArrayList<String>(((HashMap<String, String>) data.getAnswers()).values());
            ArrayList<Option> answers = new ArrayList<>();
            for(String i : ans){
                if(!TextUtils.isEmpty(i) && !i.equals("null")){
                    answers.add(new Option(i,false));
                }
            }

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
                optionsRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        optionsAdapter.notifyDataSetChanged();

                    }
                });
            };

            optionsAdapter = new OptionsAdapter(context,answers, data.isMultipleCorrectAnswers(),listener);

            optionsRecyclerView.setAdapter(optionsAdapter);
        }
    }
}

package com.saitejajanjirala.quizme.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitejajanjirala.quizme.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class OptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private boolean hasMultipleAnswers;
    private List<String> answers;

    public OptionsAdapter(Context context, HashMap<String,String> answersMap,boolean hasMultipleAnswers){
        this.context = context;
        ArrayList<String> list = new ArrayList<String>(answersMap.values());
        this.answers = list;
        this.hasMultipleAnswers = hasMultipleAnswers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(hasMultipleAnswers)
        {
           return new MultipleViewHolder(LayoutInflater.from(context).inflate(R.layout.multiple_choice,parent,false));
        }
        else {
           return new SingleViewHolder(LayoutInflater.from(context).inflate(R.layout.single_choice,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position != RecyclerView.NO_POSITION) {
            if (holder instanceof SingleViewHolder) {
                ((SingleViewHolder) holder).bindData(answers.get(position));
            }
            else if(holder instanceof MultipleViewHolder){
                ((MultipleViewHolder) holder).bindData(answers.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    class SingleViewHolder extends RecyclerView.ViewHolder{
        private final RadioButton option;
        public SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            option = itemView.findViewById(R.id.option);
            option.setOnCheckedChangeListener((compoundButton, b) -> {

            });
        }

        public void bindData(String name){
            option.setText(name);
        }
    }

    class MultipleViewHolder extends RecyclerView.ViewHolder{
        private final CheckBox option;
        public MultipleViewHolder(@NonNull View itemView) {
            super(itemView);
            option = itemView.findViewById(R.id.option);
            option.setOnCheckedChangeListener((compoundButton, b) -> {

            });
        }

        public void bindData(String name){
            option.setText(name);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(hasMultipleAnswers){
            return 1;
        }
        else{
            return 0;
        }
    }
}

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
import com.saitejajanjirala.quizme.listeners.OnOptionSelectedListener;
import com.saitejajanjirala.quizme.models.Option;

import java.util.List;

public class OptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private boolean hasMultipleAnswers;
    private List<Option> answers;

    private OnOptionSelectedListener listener;

    public OptionsAdapter(Context context, List<Option> answers, boolean hasMultipleAnswers, OnOptionSelectedListener listener){
        this.context = context;
        this.answers = answers;
        this.hasMultipleAnswers = hasMultipleAnswers;
        this.listener = listener;
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

    class SingleViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        private final RadioButton option;
        public SingleViewHolder(@NonNull View itemView) {
            super(itemView);
            option = itemView.findViewById(R.id.option);

        }

        public void bindData(Option op){
            option.setText(op.getName());
            option.setOnCheckedChangeListener(null);
            option.setChecked(op.isSelected());
            option.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            listener.onOptionSelected(getAdapterPosition(),b,hasMultipleAnswers);
        }
    }

    class MultipleViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener{
        private final CheckBox option;
        public MultipleViewHolder(@NonNull View itemView) {
            super(itemView);
            option = itemView.findViewById(R.id.option);
        }

        public void bindData(Option op){
            option.setText(op.getName());
            option.setOnCheckedChangeListener(null);
            option.setChecked(op.isSelected());
            option.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            listener.onOptionSelected(getAdapterPosition(),b,hasMultipleAnswers);

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

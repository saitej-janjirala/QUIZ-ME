package com.saitejajanjirala.quizme.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuestionCardData {
    private String question;
    private ArrayList<Option> options;
    private ArrayList<Boolean> correctAnswers;

    private String difficulty;

    private boolean isMultipleAnswers;

    public QuestionCardData(String question, ArrayList<Option> options, ArrayList<Boolean> correctAnswers, String difficulty,boolean isMultipleAnswers) {
        this.question = question;
        this.options = options;
        this.correctAnswers = correctAnswers;
        this.difficulty = difficulty;
        this.isMultipleAnswers = isMultipleAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public ArrayList<Boolean> getCorrectAnswers() {
        return correctAnswers;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isMultipleAnswers() {
        return isMultipleAnswers;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public boolean isAnswered(){
        boolean flag = false;
        for(Option i : getOptions()){
            if(i.isSelected()){
                flag = true;
                break;
            }
        }
        return flag;
    }
}

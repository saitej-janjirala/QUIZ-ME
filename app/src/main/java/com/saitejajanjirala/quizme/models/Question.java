package com.saitejajanjirala.quizme.models;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Question {

    @SerializedName("id")
    private int id;

    @SerializedName("question")
    private String question;

    @SerializedName("description")
    private String description;

    @SerializedName("answers")
    private HashMap<String, String> answers;

    @SerializedName("multiple_correct_answers")
    private boolean multipleCorrectAnswers;

    @SerializedName("correct_answers")
    private HashMap<String, Boolean> correctAnswers;

    @SerializedName("explanation")
    private String explanation;

    @SerializedName("tip")
    private String tip;

    @SerializedName("tags")
    private List<Tag> tags;

    @SerializedName("category")
    private String category;

    @SerializedName("difficulty")
    private String difficulty;

    // Getters for all fields
    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, String> getAnswers() {
        return answers;
    }

    public boolean isMultipleCorrectAnswers() {
        return multipleCorrectAnswers;
    }

    public HashMap<String, Boolean> getCorrectAnswers() {
        return correctAnswers;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getTip() {
        return tip;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }


    public ArrayList<Option> getOptions(){
        Map<String,String> map = new TreeMap<>(getAnswers());
        HashMap<String,String> hMap = new HashMap<>(map);
        ArrayList<String> ans = new ArrayList<String>(hMap.values());
        ArrayList<Option> answers = new ArrayList<>();
        for(String i : ans){
            if(!TextUtils.isEmpty(i) && !i.equals("null")){
                answers.add(new Option(i,false));
            }
        }
        return answers;
    }

    public ArrayList<Boolean> getCorrectAnswersList(){
        List<String> arr = new ArrayList<>();
        for(Map.Entry<String,Boolean> entry : correctAnswers.entrySet()){
            arr.add(entry.getKey());
        }
        Collections.sort(arr);
        ArrayList<Boolean> boolArr = new ArrayList<>();
        for (String i : arr){
            boolArr.add(correctAnswers.get(i));
        }
        return boolArr;
    }
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", description='" + description + '\'' +
                ", answers=" + answers +
                ", multipleCorrectAnswers=" + multipleCorrectAnswers +
                ", correctAnswers=" + correctAnswers +
                ", explanation='" + explanation + '\'' +
                ", tip='" + tip + '\'' +
                ", tags=" + tags.toString() +
                ", category='" + category + '\'' +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }





}

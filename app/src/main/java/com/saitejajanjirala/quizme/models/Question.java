package com.saitejajanjirala.quizme.models;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, String> correctAnswers;

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

    public Map<String, String> getCorrectAnswers() {
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

package com.saitejajanjirala.quizme.models;

import java.io.Serializable;

public class TestResult implements Serializable {
    private String category;
    private int score;
    private String difficulty;
    private String date;

    public TestResult(){

    }
    public TestResult(String category, int score, String difficulty, String date) {
        this.category = category;
        this.score = score;
        this.difficulty = difficulty;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public int getScore() {
        return score;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getDate() {
        return date;
    }
}

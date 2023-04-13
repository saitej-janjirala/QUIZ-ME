package com.saitejajanjirala.quizme.network;

public enum DIFFICULTY {
    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");
    private String difficulty;
    DIFFICULTY(String difficulty){
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }
}

package com.saitejajanjirala.quizme.models;

import androidx.annotation.NonNull;

public class Option {
    private String name;
    private boolean isSelected;

    public Option(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Option{" +
                "name='" + name + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}

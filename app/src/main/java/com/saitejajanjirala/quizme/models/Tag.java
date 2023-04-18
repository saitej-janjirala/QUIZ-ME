package com.saitejajanjirala.quizme.models;

import com.google.gson.annotations.SerializedName;

public class Tag {
    @SerializedName("name")
    private String tag;

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tag='" + tag + '\'' +
                '}';
    }
}

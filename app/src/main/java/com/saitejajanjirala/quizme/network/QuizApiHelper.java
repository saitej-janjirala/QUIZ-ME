package com.saitejajanjirala.quizme.network;


import com.saitejajanjirala.quizme.BuildConfig;

public class QuizApiHelper {

    private static ApiService instance;

    public static synchronized ApiService getInstance(){
        if (instance == null) {
            instance = QuizApiClient.getClient(BuildConfig.API_KEY).create(ApiService.class);
        }
        return instance;
    }


}

package com.saitejajanjirala.quizme.network;

import com.saitejajanjirala.quizme.models.Question;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("questions")
    Single<List<Question>> getQuestions(
            @Query("category") String category,
            @Query("tags") String tags,
            @Query("limit") int limit,
            @Query("difficulty") String difficulty);
}

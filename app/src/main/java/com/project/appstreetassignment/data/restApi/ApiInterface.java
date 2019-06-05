package com.project.appstreetassignment.data.restApi;

import com.project.appstreetassignment.data.model.network.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("top-headlines")
    Single<NewsResponse> fetchNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );

}

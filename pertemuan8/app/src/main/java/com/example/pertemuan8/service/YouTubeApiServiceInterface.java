package com.example.pertemuan8.service;

import com.example.pertemuan8.model.google.YouTubeApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YouTubeApiServiceInterface {

    @GET("search")
    Call<YouTubeApiResponse> getSearchResult(
            @Query("key") String key,
            @Query("channelId") String channelId,
            @Query("type") String type
    );
}

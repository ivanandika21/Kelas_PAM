package com.example.pertemuan8.service;

import com.example.pertemuan8.model.publicapi.PublicApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PublicApiServiceInterface {

    @GET("entries")
    Call<PublicApiResponse> getAllEntries();

    @GET("random")
    Call<PublicApiResponse> getEntryRandomly();
}

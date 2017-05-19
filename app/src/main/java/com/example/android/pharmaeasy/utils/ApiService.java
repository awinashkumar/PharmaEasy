package com.example.android.pharmaeasy.utils;

import com.example.android.pharmaeasy.model.PageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Awinash on 17-05-2017.
 */

public interface ApiService {

    @GET("/api/users")
    Call<PageResponse> getPageDetails(@Query("page") int pageIndex);
}

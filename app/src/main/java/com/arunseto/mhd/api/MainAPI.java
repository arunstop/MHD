package com.arunseto.mhd.api;

import com.arunseto.mhd.models.News;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainAPI {

    @GET("user")
    Call<UserResponse> showUser(
            @Query("email") String email,
            @Query("password") String password
    );

}

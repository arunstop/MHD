package com.arunseto.mhd.api;

import com.arunseto.mhd.models.News;
import com.arunseto.mhd.models.NewsArticle;
import com.arunseto.mhd.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("?apiKey=88ea4c944312484386c20c74b423c008")
    Call<News> showNews(@Query("q") String q);

}

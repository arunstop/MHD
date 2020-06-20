package com.arunseto.mhd.api;

import com.arunseto.mhd.models.News;
import com.arunseto.mhd.models.NewsArticle;
import com.arunseto.mhd.models.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsAPI {

    @GET("everything?apiKey=88ea4c944312484386c20c74b423c008&q=kesehatan%20mental")
    Call<News> showNews();

}

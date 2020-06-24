package com.arunseto.mhd.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arunstop on 24-May-19.
 */

public class NewsClient {
    private static final String BASE_URL = "https://newsapi.org/v2/everything/";
    private static NewsClient mInstance;
    private Retrofit retrofit;

    private NewsClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized NewsClient getInstance() {
        if (mInstance == null) {
            mInstance = new NewsClient();
        }
        return mInstance;
    }

    public NewsAPI getApi() {
        return retrofit.create(NewsAPI.class);
    }
}

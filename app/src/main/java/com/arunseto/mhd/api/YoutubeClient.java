package com.arunseto.mhd.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arunstop on 24-May-19.
 */

public class YoutubeClient {

    // 20200726111815
    // https://www.googleapis.com/youtube/v3/search?key=AIzaSyDQj3SuEJ-GPvJkcXK_reIB5fn3hmH0-tw&safeSearch=strict&part=snippet&maxResults=25&q=mental%20health

    //    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    private static final String BASE_URL = "http://192.168.1.3/ytdummy/";
    private static YoutubeClient mInstance;
    private Retrofit retrofit;

    private YoutubeClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized YoutubeClient getInstance() {
        if (mInstance == null) {
            mInstance = new YoutubeClient();
        }
        return mInstance;
    }

    public YoutubeAPI getApi() {
        return retrofit.create(YoutubeAPI.class);
    }
}

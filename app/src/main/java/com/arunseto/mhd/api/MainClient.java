package com.arunseto.mhd.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arunstop on 24-May-19.
 */

public class MainClient {
    // NOX EMULATOR
    private static final String BASE_URL = "http://172.17.100.2/mhd/api/";
    // NON NOX
//    private static final String BASE_URL = "http://196.168.1.2/mhd/api/";
    private static MainClient mInstance;
    private Retrofit retrofit;

    private MainClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized MainClient getInstance() {
        if (mInstance == null) {
            mInstance = new MainClient();
        }
        return mInstance;
    }

    public MainAPI getApi() {
        return retrofit.create(MainAPI.class);
    }
}

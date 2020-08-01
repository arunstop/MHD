package id.ac.stiki.doleno.mhd.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arunstop on 24-May-19.
 */

public class ImgurClient {
    // NOX EMULATOR (works)
//    private static final String BASE_URL = "http://172.17.100.2/MHD-API/api/";
    // NON NOX (also works) <-- this is preferred
//    private static final String BASE_URL = "http://192.168.1.3:80/MHD-API/api/";
    // HOSTING
    private static final String BASE_URL = "https://api.imgur.com/3/";
    private static ImgurClient mInstance;
    private Retrofit retrofit;

    private ImgurClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ImgurClient getInstance() {
        if (mInstance == null) {
            mInstance = new ImgurClient();
        }
        return mInstance;
    }

    public ImgurAPI getApi() {
        return retrofit.create(ImgurAPI.class);
    }
}

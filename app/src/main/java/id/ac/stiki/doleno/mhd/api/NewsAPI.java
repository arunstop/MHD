package id.ac.stiki.doleno.mhd.api;

import id.ac.stiki.doleno.mhd.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {

    @GET("?apiKey=88ea4c944312484386c20c74b423c008")
    Call<News> showNews(@Query("q") String q);

}

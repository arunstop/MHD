package id.ac.stiki.doleno.mhd.api;

import id.ac.stiki.doleno.mhd.models.ytmodels.YoutubeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeAPI {

    //    AIzaSyD7ubYW6XJgv3rqZOmb1jsRfMBySZ1DB2o warta
//    AIzaSyDQj3SuEJ-GPvJkcXK_reIB5fn3hmH0-tw
    String key = "AIzaSyDQj3SuEJ-GPvJkcXK_reIB5fn3hmH0-tw";

    @GET("search.json?key=" + key + "&safeSearch=strict&part=snippet&safeSearch=strict")
//    @GET("search?key="+key+"&safeSearch=strict&part=snippet&safeSearch=strict")
    Call<YoutubeResponse> showVideos(@Query("q") String q,
                                     @Query("maxResults") int maxResults,
                                     @Query("pageToken") String pageToken);

}

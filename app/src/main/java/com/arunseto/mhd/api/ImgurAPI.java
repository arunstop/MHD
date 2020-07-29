package com.arunseto.mhd.api;

import com.arunseto.mhd.models.ImgurResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImgurAPI {

    //    AIzaSyD7ubYW6XJgv3rqZOmb1jsRfMBySZ1DB2o warta
//    AIzaSyDQj3SuEJ-GPvJkcXK_reIB5fn3hmH0-tw


//    @Multipart
//    @POST("image.json")
//    Call<ImgurResponse> uploadImg(
//            @Header("Authorization") String authorization,
//            @Part MultipartBody.Part image);


    @Multipart
    @POST("image")
    Call<ImgurResponse> uploadImg(
            @Header("Authorization") String authorization, @Part MultipartBody.Part image);

}

package com.arunseto.mhd.api;

import com.arunseto.mhd.models.DefaultResponse;
import com.arunseto.mhd.models.News;
import com.arunseto.mhd.models.NoteResponse;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.models.UserResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface MainAPI {

    @GET("user")
    Call<UserResponse> showUser(
            @Query("email") String email,
            @Query("password") String password
    );

    @FormUrlEncoded
    @POST("user/register")
    Call<UserResponse> registerUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("no_telp") String no_telp,
            @Field("sex") int sex,
            @Field("birth_date") String birth_date,
            @Field("city") String city,
            @Field("photo_url") String photo_url,
            @Field("role") int role,
            @Field("last_login") String last_login,
            @Field("type_login") int type_login,
            @Field("created_at") String created_at
    );

    @FormUrlEncoded
    @PUT("user/auth")
    Call<UserResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("last_login") String last_login,
            @Field("type_login") int type_login
    );

    @GET("note/show")
    Call<NoteResponse> showNote(
            @Query("id_user") int id_user
    );

    @FormUrlEncoded
    @POST("note/add")
    Call<DefaultResponse> addNote(
            @Field("id_user") int id_user,
            @Field("judul_catatan") String judul_catatan,
                    @Field("isi_catatan") String isi_catatan,
                    @Field("created_at") String created_at
    );

    @FormUrlEncoded
    @POST("note/delete")
    Call<DefaultResponse> deleteNote(
        @Field("id_catatan") int id
    );


}

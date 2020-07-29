package com.arunseto.mhd.api;

import androidx.annotation.Nullable;

import com.arunseto.mhd.models.ArticleResponse;
import com.arunseto.mhd.models.DefaultResponse;
import com.arunseto.mhd.models.DisorderResponse;
import com.arunseto.mhd.models.NoteResponse;
import com.arunseto.mhd.models.PsychiatristResponse;
import com.arunseto.mhd.models.QuestionResponse;
import com.arunseto.mhd.models.TestDetailResponse;
import com.arunseto.mhd.models.TestResponse;
import com.arunseto.mhd.models.TestResultResponse;
import com.arunseto.mhd.models.UserResponse;
import com.arunseto.mhd.models.VideoResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    @POST("user/authGoogle")
    Call<UserResponse> loginGoogle(
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
    @POST("user/auth")
    Call<UserResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("last_login") String last_login,
            @Field("type_login") int type_login
    );

    @FormUrlEncoded
    @POST("user/update")
    Call<UserResponse> updateUser(
            @Field("id_user") int id_user,
            @Field("email") String email,
            @Field("password") String password,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("no_telp") String no_telp,
            @Field("sex") int sex,
            @Field("birth_date") String birth_date,
            @Field("city") String city,
            @Field("photo_url") String photo_url
    );

    @FormUrlEncoded
    @POST("user/delete")
    Call<DefaultResponse> deleteUser(
            @Field("id_user") int id_user
    );


    @GET("note/show")
    Call<NoteResponse> showNotes(
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

    @GET("psychiatrist/show")
    Call<PsychiatristResponse> showPsychiatrists();

    @GET("symptom/show")
    Call<QuestionResponse> showSymptom();

    @GET("symptom/show")
    Call<QuestionResponse> loadQuiz(
            @Query("id_gejala_detail") int id_gejala_detail
    );

    @GET("disorder/show")
    Call<DisorderResponse> showDisorder(
            @Nullable @Query("id_penyakit") Integer id_penyakit
    );

    @FormUrlEncoded
    @POST("test/add")
    Call<TestResponse> addTest(
            @Field("id_user") int id_user,
            @Field("result") int result
    );

    @GET("test/showFullInfo")
    Call<TestResponse> showTests(
            @Query("id_user") Integer id_user
    );

    @FormUrlEncoded
    @POST("test/addDetail")
    Call<TestDetailResponse> addTestDetail(
            @Field("id_gejala_detail") int id_gejala_detail,
            @Field("id_tes") int id_tes,
            @Field("choice") int choice
    );

    @GET("test/showResult")
    Call<TestResultResponse> showTestResult(
            @Query("id_tes") int id_tes
    );

    @GET("test/showResultDetail")
    Call<TestDetailResponse> showTestResultDetail(
            @Query("id_tes") int id_tes
    );

    @GET("article/show")
    Call<ArticleResponse> showArticles(
            @Query("limit") int limit
    );

    @GET("video/show")
    Call<VideoResponse> showVideos(
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}

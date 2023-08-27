package com.example.truyentranh_asmapp.api;

import com.example.truyentranh_asmapp.models.Comment;
import com.example.truyentranh_asmapp.models.ListComics;
import com.example.truyentranh_asmapp.models.MessComment;
import com.example.truyentranh_asmapp.models.MessegerUser;
import com.example.truyentranh_asmapp.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    ApiService apiService = new Retrofit.Builder().baseUrl("http://10.0.2.2:3000/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService.class);

    @GET("user/login")
    Call<MessegerUser> Login(@Query("username") String username
            , @Query("password") String password);


    @POST("user/register")
    Call<User> Register(@Body User user);

    @GET("comic")
    Call<ListComics> getComics();

    @PUT("comicfa/add/{idcomic}")
    Call<User> addFavorite(@Path("idcomic") String idTruyen , @Query("status") Boolean status);

    @GET("comicfa")
    Call<ListComics> getComicsFa();

    @POST("comment/add")
    Call<User> AddComment(@Query("comment_time") String commentTime,
                          @Query("id_truyen") String idTruyen,
                          @Query("id_use") String idUser,
                          @Query("content") String Content);

    @GET("findcomment")
    Call<Comment> getComment(@Query("id_truyen") String idTruyen);

    @PUT("comment/edit/{idcomment}")
    Call<User> updateComment(@Path("idcomment") String idComment ,
                             @Query("comment_time") String timeComment ,
                             @Query("id_truyen") String idTruyen ,
                             @Query("id_use") String idUser,
                             @Query("content") String content);

    @DELETE("comment/delete/{idcomment}")
    Call<User> deleteComment(@Path("idcomment") String idComment);

    @PUT("user/edit/{iduser}")
    Call<User> updateUser(@Path("iduser") String iduser ,@Body User user);

    @POST("user/changerpass")
    Call<User> changerPass(@Query("username") String username,@Query("password") String password ,@Query("newpassword") String newPassword);
}

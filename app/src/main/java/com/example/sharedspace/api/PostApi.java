package com.example.sharedspace.api;

import androidx.annotation.NonNull;

import com.example.sharedspace.ApiModels.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostApi {
    @GET("post/")
    Call<List<Post>> getAllPost();


    @GET("post/")
    Call<List<Post>> getUserPost(@Query("AuthToken") String  AuthToken);

    @FormUrlEncoded()
    @POST("post/")
    @NonNull
    Call<Post> create(@Query("AuthToken") String AuthToken, @Field("title") String title ,@Field("content") String content);

    @DELETE("post/")
    Call<Void>  delete (@Query("AuthToken") String AuthToken,@Query("id")String id);
}


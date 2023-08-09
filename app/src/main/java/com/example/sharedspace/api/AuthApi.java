package com.example.sharedspace.api;

import com.example.sharedspace.ApiModels.Authentication;
import com.example.sharedspace.ApiModels.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApi {
    @POST("login/")
    Call<Authentication>  login(@Query("username") String username ,@Query("password") String password);


    @GET("user/")
    Call<User>  getuser (@Query("AuthToken") String AuthToken);
}



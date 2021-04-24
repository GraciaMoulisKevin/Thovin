package com.example.thovin.services;

import com.example.thovin.POJO.auth.LoginPOJO;
import com.example.thovin.POJO.auth.RegisterPOJO;
import com.example.thovin.results.AuthResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AuthServices{
    @POST("auth")
    Call<AuthResult> login(@Body LoginPOJO body);

    @PUT("auth")
    Call<AuthResult> register(@Body RegisterPOJO body);
}

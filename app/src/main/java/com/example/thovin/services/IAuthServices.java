package com.example.thovin.services;

import com.example.thovin.models.LoginRequest;
import com.example.thovin.models.RegisterRequest;
import com.example.thovin.models.AuthResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IAuthServices {
    @POST("auth")
    Call<AuthResult> login(@Body LoginRequest body);

    @PUT("auth")
    Call<AuthResult> register(@Body RegisterRequest body);
}

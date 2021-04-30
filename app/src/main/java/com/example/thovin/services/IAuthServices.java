package com.example.thovin.services;

import com.example.thovin.models.LoginModel;
import com.example.thovin.models.RegisterModel;
import com.example.thovin.models.AuthResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IAuthServices {
    @POST("auth")
    Call<AuthResult> login(@Body LoginModel body);

    @PUT("auth")
    Call<AuthResult> register(@Body RegisterModel body);
}

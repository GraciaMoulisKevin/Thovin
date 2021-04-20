package com.example.thovin.services;

import com.example.thovin.POJO.LoginPOJO;
import com.example.thovin.POJO.RegisterPOJO;
import com.example.thovin.models.AuthResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AuthServices {
    @POST("auth")
    Call<AuthResponseModel> login(@Body LoginPOJO body);

    @PUT("auth")
    Call<AuthResponseModel> register(@Body RegisterPOJO body);

//    @PUT("auth")
//    Call<UserModel> getUserRegister();
}

package com.example.thovin.services;

import com.example.thovin.models.user.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RestaurantServices {
    @GET("restaurants")
    Call<ArrayList<UserModel>> getRestaurants(@Header("Authorization") String token);
}

package com.example.thovin.services;

import com.example.thovin.models.AddItemRequest;
import com.example.thovin.models.CartModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ICartServices {
    @GET("users/me/cart")
    Call<CartModel> getUserCart(@Header("Authorization") String token);

    @POST("users/me/cart")
    Call<CartModel> addItem(@Header("Authorization") String token, @Body AddItemRequest item);
}

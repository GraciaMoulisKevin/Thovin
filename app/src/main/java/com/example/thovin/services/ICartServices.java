package com.example.thovin.services;

import com.example.thovin.models.CartModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ICartServices {
    @GET("cart")
    Call<CartModel> getUserCart();
}

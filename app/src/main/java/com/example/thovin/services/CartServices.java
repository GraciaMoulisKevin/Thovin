package com.example.thovin.services;

import com.example.thovin.models.cart.CartModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CartServices {
    @GET("cart")
    Call<CartModel> getUserCart();
}

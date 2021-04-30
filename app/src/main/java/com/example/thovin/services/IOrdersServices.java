package com.example.thovin.services;

import com.example.thovin.models.OrderModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IOrdersServices {
    @GET("orders")
    Call<OrderModel> getOrders();

    @POST("orders")
    Call<OrderModel> addOrders();
}

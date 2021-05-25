package com.example.thovin.services;

import com.example.thovin.models.OrderRequest;
import com.example.thovin.models.OrderResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IOrderServices {
    @GET("orders")
    Call<ArrayList<OrderResult>> getOrder(@Header("Authorization") String token);

    @POST("orders")
    Call<OrderResult> postOrder(@Header("Authorization") String token, @Body OrderRequest order);

    @POST("orders/{order_id}")
    Call<String> updateStatus(@Header("Authorization") String token,
                              @Path(value="order_id", encoded = true) String orderId);
}

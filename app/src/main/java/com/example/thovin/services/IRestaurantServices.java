package com.example.thovin.services;

import com.example.thovin.models.MenuModel;
import com.example.thovin.models.ProductModel;
import com.example.thovin.models.ProductResult;
import com.example.thovin.models.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRestaurantServices {
    @GET("restaurants")
    Call<ArrayList<UserModel>> getRestaurants(@Header("Authorization") String token);

    @GET("restaurants/{restaurant_id}/menus")
    Call<ArrayList<MenuModel>> getMenus(@Header("Authorization") String token,
                                        @Path(value="restaurant_id", encoded = true) String restaurantId);

    @GET("restaurants/{restaurant_id}/products")
    Call<ArrayList<ProductModel>> getProducts(@Header("Authorization") String token,
                                              @Path(value="restaurant_id", encoded = true) String restaurantId);

    @POST("restaurants/{restaurant_id}/products")
    Call<ArrayList<ProductModel>> postProduct(@Header("Authorization") String token,
                                    @Path(value="restaurant_id", encoded = true) String restaurantId,
                                    @Body ProductModel product);

    @DELETE("restaurants/{restaurant_id}/products/{product_id}")
    Call<ProductModel> deleteProduct(@Header("Authorization") String token,
                                     @Path(value="restaurant_id", encoded = true) String restaurantId,
                                     @Path(value="product_id", encoded = true) String productId);
}

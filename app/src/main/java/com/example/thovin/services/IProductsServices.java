package com.example.thovin.services;

import com.example.thovin.models.restaurant.MenuModel;
import com.example.thovin.models.restaurant.ProductModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IProductsServices {
    @GET("/products")
    Call<ProductModel[]> getProduct();

    @GET("/menus")
    Call<MenuModel[]> getMenus();

    @POST("/menus")
    Call<MenuModel> addMenus();
}

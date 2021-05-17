package com.example.thovin.services;

import com.example.thovin.models.MenuModel;
import com.example.thovin.models.ProductModel;

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

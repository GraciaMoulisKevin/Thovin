package com.example.thovin.models.cart;

import com.example.thovin.models.restaurant.MenuModel;
import com.example.thovin.models.user.UserModel;
import com.google.gson.annotations.SerializedName;

public class CartModel {
    @SerializedName("_id")
    public Object id;
    public UserModel restaurant;
    public UserModel client;
    public MenuModel[] menus;
}

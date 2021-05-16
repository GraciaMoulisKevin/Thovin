package com.example.thovin.models.cart;

import com.example.thovin.models.user.UserModel;
import com.google.gson.annotations.SerializedName;

public class CartModel {
    @SerializedName("_id")
    public String id;
    public UserModel restaurant;
    public UserModel client;
    public String[] menus;
}

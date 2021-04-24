package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

public class CartModel {
    @SerializedName("_id")
    public Object id;
    public UserModel restaurant;
    public UserModel client;
    public MenuModel[] menus;
}

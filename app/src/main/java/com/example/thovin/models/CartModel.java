package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CartModel {
    @SerializedName("_id")
    public String id;
    public UserModel restaurant;
    public UserModel client;
    public ArrayList<MenuModel> menus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(UserModel restaurant) {
        this.restaurant = restaurant;
    }

    public UserModel getClient() {
        return client;
    }

    public void setClient(UserModel client) {
        this.client = client;
    }

    public ArrayList<MenuModel> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<MenuModel> menus) {
        this.menus = menus;
    }
}

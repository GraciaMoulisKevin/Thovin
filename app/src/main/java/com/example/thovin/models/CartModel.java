package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CartModel {
    @SerializedName("_id")
    public String id;
    public String restaurantId;
    public String clientId;
    public ArrayList<MenuModel> menus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ArrayList<MenuModel> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<MenuModel> menus) {
        this.menus = menus;
    }

    /**
     * Send total price of the cart.
     */
    public String getPrice(){
        double sum = 0;
        for (MenuModel m : this.getMenus()){
            for (ProductResult p : m.getProducts()){
                sum += p.getPrice();
            }
        }
        return String.format("%.2f", sum);
    }
}

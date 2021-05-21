package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderResult {
    @SerializedName("_id")
    public String id;
    public String status;
    public UserModel client;
    @SerializedName("deliverer")
    public String delivererId;
    public UserModel restaurant;
    public ArrayList<MenuModel> menus;
    public CreditCardModel payment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserModel getClient() {
        return client;
    }

    public void setClient(UserModel client) {
        this.client = client;
    }

    public String getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(String delivererId) {
        this.delivererId = delivererId;
    }

    public UserModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(UserModel restaurant) {
        this.restaurant = restaurant;
    }

    public ArrayList<MenuModel> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<MenuModel> menus) {
        this.menus = menus;
    }

    public CreditCardModel getPayment() {
        return payment;
    }

    public void setPayment(CreditCardModel payment) {
        this.payment = payment;
    }
}

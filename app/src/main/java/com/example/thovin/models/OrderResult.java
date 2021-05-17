package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderResult {
    @SerializedName("_id")
    public String id;
    public String status;
    @SerializedName("client")
    public String clientId;
    @SerializedName("deliverer")
    public String delivererId;
    @SerializedName("restaurant")
    public String restaurantId;
    public ArrayList<String> menus;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDelivererId() {
        return delivererId;
    }

    public void setDelivererId(String delivererId) {
        this.delivererId = delivererId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public ArrayList<String> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<String> menus) {
        this.menus = menus;
    }

    public CreditCardModel getPayment() {
        return payment;
    }

    public void setPayment(CreditCardModel payment) {
        this.payment = payment;
    }
}

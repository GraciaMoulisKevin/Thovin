package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

public class OrderModel {
    @SerializedName("cart")
    public Object cartId;
    @SerializedName("credit_cart")
    public CreditCartModel creditCart;
}

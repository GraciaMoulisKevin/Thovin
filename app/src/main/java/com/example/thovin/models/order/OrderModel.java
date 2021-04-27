package com.example.thovin.models.order;

import com.example.thovin.models.payment.CreditCartModel;
import com.google.gson.annotations.SerializedName;

public class OrderModel {
    @SerializedName("cart")
    public Object cartId;
    @SerializedName("credit_cart")
    public CreditCartModel creditCart;
}

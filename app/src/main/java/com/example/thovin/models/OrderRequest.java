package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

public class OrderRequest {
    @SerializedName("cart")
    public String cartId;
    @SerializedName("credit_card")
    public CreditCardModel creditCard;

    public OrderRequest(String cartId, CreditCardModel creditCard) {
        this.cartId = cartId;
        this.creditCard = creditCard;
    }
}

package com.example.thovin.models;

public class AddItemRequest {
    final String menu;
    final String restaurant;

    public AddItemRequest(String menu, String restaurant) {
        this.menu = menu;
        this.restaurant = restaurant;
    }
}

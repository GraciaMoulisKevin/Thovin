package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    public static String TYPE_CLIENT = "client";
    public static String TYPE_DELIVERER = "deliverer";
    public static String TYPE_RESTAURANT = "restaurant";

    @SerializedName("first_name")
    private final String firstName;
    @SerializedName("last_name")
    private final String lastName;
    private final String email;
    private final String password;
    @SerializedName("restaurant_name")
    private final String restaurantName;
    private final String phone;
    private final AddressModel address;
    private final String type;

    public RegisterRequest(String firstName, String lastName, String email, String password, String phone, AddressModel address, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.restaurantName = null;
        this.address = address;
        this.type = type;
    }

    public RegisterRequest(String firstName, String lastName, String email, String password, String phone, String restaurantName, AddressModel address, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.restaurantName = restaurantName;
        this.address = address;
        this.type = type;
    }

    // --- Getters

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getPhone() {
        return phone;
    }

    public AddressModel getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }
}

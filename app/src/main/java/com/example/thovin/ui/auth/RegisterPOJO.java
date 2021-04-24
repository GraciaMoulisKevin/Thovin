package com.example.thovin.ui.auth;

import com.example.thovin.models.AddressModel;
import com.google.gson.annotations.SerializedName;

public class RegisterPOJO {
    public static String TYPE_CLIENT = "client";
    public static String TYPE_DELIVERER = "deliverer";
    public static String TYPE_RESTAURANT = "restaurant";

    @SerializedName("first_name")
    private final String firstName;
    @SerializedName("last_name")
    private final String lastName;
    private final String email;
    private final String password;
    private final String phone;
    private final AddressModel address;
    private final String type;

    public RegisterPOJO(String type, String firstName, String lastName, String email, String password, String phone) {
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = null;
    }

    public RegisterPOJO(String type, String firstName, String lastName, String email, String password, String phone, AddressModel address) {
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    // --- Getters

    public String getType() {
        return type;
    }

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

    public String getPhone() {
        return phone;
    }

    public AddressModel getAddress() {
        return address;
    }
}

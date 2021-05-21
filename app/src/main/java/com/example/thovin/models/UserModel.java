package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserModel {

    public static String CLIENT = "client";
    public static String DELIVERER = "deliverer";
    public static String RESTAURANT = "restaurant";

    @SerializedName("_id")
    public String id;
    public Boolean admin;
    public String type;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
    public String email;
    public String phone;
    public AddressModel address;

    // --- Additional Restaurant value
    @SerializedName("restaurant_name")
    public String restaurantName;
    @SerializedName("products")
    public ArrayList<String> productsId;
    public ArrayList<ProductModel> productsModels;
    @SerializedName("menus")
    public ArrayList<String> menusId;
    public ArrayList<MenuModel> menusModels;


    // --- GETTERS AND SETTERS
    public Object getId() {
        return id;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public String getFullAddress() {
        return address.street + ", "
                + address.additional + ", "
                + address.zip + " " + address.city + ", "
                + address.country;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public ArrayList<String> getProductsId() {
        return productsId;
    }

    public void setProductsId(ArrayList<String> productsId) {
        this.productsId = productsId;
    }

    public ArrayList<ProductModel> getProductsModels() {
        return productsModels;
    }

    public void setProductsModels(ArrayList<ProductModel> productsModels) {
        this.productsModels = productsModels;
    }

    public ArrayList<String> getMenusId() {
        return menusId;
    }

    public void setMenusId(ArrayList<String> menusId) {
        this.menusId = menusId;
    }

    public ArrayList<MenuModel> getMenus() {
        return menusModels;
    }

    public void setMenus(ArrayList<MenuModel> menus) {
        this.menusModels = menus;
    }
}

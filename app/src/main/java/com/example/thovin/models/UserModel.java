package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("_id")
    public Object id;
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
    public ProductModel[] products;
    public MenuModel[] menus;


    // --- GETTERS AND SETTERS
    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
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

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public ProductModel[] getProducts() {
        return products;
    }

    public void setProducts(ProductModel[] products) {
        this.products = products;
    }

    public MenuModel[] getMenus() {
        return menus;
    }

    public void setMenus(MenuModel[] menus) {
        this.menus = menus;
    }
}

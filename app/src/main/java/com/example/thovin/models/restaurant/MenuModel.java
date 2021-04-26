package com.example.thovin.models.restaurant;

import com.google.gson.annotations.SerializedName;

public class MenuModel {
    @SerializedName("_id")
    public Object id;
    public String name;
    public String description;
    @SerializedName("img_url")
    public String imgURL;
    public ProductModel[] products;
    public String category;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public ProductModel[] getProducts() {
        return products;
    }

    public void setProducts(ProductModel[] products) {
        this.products = products;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

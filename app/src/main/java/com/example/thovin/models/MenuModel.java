package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MenuModel {
    @SerializedName("_id")
    public String id;
    public String name;
    public String description;
    @SerializedName("img_url")
    public String imgURL;
    public ArrayList<ProductResult> products;
    public String category;

    public String getId() {
        return id;
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

    public ArrayList<ProductResult> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductResult> products) {
        this.products = products;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

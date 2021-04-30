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
    @SerializedName("products")
    public ArrayList<String> productsId;
    // public ArrayList<ProductModel> products
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

    public ArrayList<String> getProductsId() {
        return productsId;
    }

    public void setProductsId(ArrayList<String> productsId) {
        this.productsId = productsId;
    }

//    public ArrayList<ProductModel> getProducts() {
//        return products;
//    }
//
//    public void setProducts(ArrayList<ProductModel> products) {
//        this.products = products;
//    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

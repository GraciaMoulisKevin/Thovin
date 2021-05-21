package com.example.thovin.models;

import com.example.thovin.R;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductModel {
    @SerializedName("_id")
    public String id;
    public String name;
    public String description;
    @SerializedName("img_url")
    public String imgURL;
    public ArrayList<ProductResult> extras;
    public String type;

    public ProductModel(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

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

    public ArrayList<ProductResult> getExtras() {
        return extras;
    }

    public void setExtras(ArrayList<ProductResult> extras) {
        this.extras = extras;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

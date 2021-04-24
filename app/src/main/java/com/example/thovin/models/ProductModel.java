package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

public class ProductModel {
    public String name;
    public String description;
    @SerializedName("img_url")
    public String imgURL;
    public ProductModel[] extras;
    public String type;

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

    public ProductModel[] getExtras() {
        return extras;
    }

    public void setExtras(ProductModel[] extras) {
        this.extras = extras;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

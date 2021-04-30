package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductModel {
    @SerializedName("_id")
    public String id;
    public String name;
    public String description;
    @SerializedName("img_url")
    public String imgURL;
    @SerializedName("extras")
    public ArrayList<String> extrasId;
    // public ArrayList<ProductModel> extras
    public String type;

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

    public ArrayList<String> getExtrasId() {
        return extrasId;
    }

    public void setExtrasId(ArrayList<String> extrasId) {
        this.extrasId = extrasId;
    }

    //    public ArrayList<ProductModel> getExtras() {
    //        return extras;
    //    }
    //
    //    public void setExtras(ArrayList<ProductModel> extras) {
    //        this.extras = extras;
    //    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.example.thovin.models.restaurant;

import com.google.gson.annotations.SerializedName;

public class ProductModel {
    @SerializedName("_id")
    public String id;
    public String name;
    public String description;
    @SerializedName("img_url")
    public String imgURL;
    @SerializedName("extras")
    public String[] extrasId;
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

    public String[] getExtrasId() {
        return extrasId;
    }

    public void setExtrasId(String[] extrasId) {
        this.extrasId = extrasId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.example.thovin;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("_id")
    public Object id;

    @SerializedName("name")
    public String name;

    @SerializedName("firstname")
    public String firstName;

    // --- GETTERS
    public Object getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }
}

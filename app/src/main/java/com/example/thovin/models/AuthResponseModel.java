package com.example.thovin.models;

import com.google.gson.annotations.SerializedName;

public class AuthResponseModel {

    public String token;

    public long expires;

    public UserModel user;
    
    public String getToken() {
        return token;
    }

    public long getExpires() {
        return expires;
    }

    public UserModel getUser() {
        return user;
    }
}

package com.example.thovin.results;

import com.example.thovin.models.UserModel;

public class AuthResult {

    public final static String TYPE_CLIENT = "client";
    public final static String TYPE_DELIVERER = "deliverer";
    public final static String TYPE_RESTAURANT = "restaurant";

    public int type;
    public boolean success;

    // --- On success
    public String token;
    public long expires;

    // --- On failure
    public int resCode;
    public String code;
    public String message;
    public String[] fields;
    public UserModel user;

    public AuthResult() {
    }

    public AuthResult(int type) { this.type = type; }

    public AuthResult(int type, int resCode) {
        this.type = type;
        this.resCode = resCode;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}

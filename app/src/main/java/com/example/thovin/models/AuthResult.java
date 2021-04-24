package com.example.thovin.models;

public class AuthResult {

    public int type;
    public boolean success;

    // --- On success
    public String token;
    public long expires;
    public UserModel user;

    // --- On failure
    public int resCode;
    public String code;
    public String message;
    public String[] fields;

    public AuthResult() {
    }

    public AuthResult(int type) { this.type = type; }

    public AuthResult(int type, int resCode) {
        this.type = type;
        this.resCode = resCode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getToken() {
        return token;
    }

    public long getExpires() {
        return expires;
    }

    public UserModel getUser() {
        return user;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String[] getFields() {
        return fields;
    }
}

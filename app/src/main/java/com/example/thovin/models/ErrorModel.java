package com.example.thovin.models;

public class ErrorModel {
    public int resCode;
    public String code;
    public String message;

    public ErrorModel(int resCode) {
        this.resCode = resCode;
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
}

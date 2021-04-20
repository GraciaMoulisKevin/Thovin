package com.example.thovin.models;

import java.util.ArrayList;

public class ErrResponseModel {
    private String code;
    private String message;
    private String[] fields;

    public ErrResponseModel(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrResponseModel(String code, String message, String[] fields) {
        this.code = code;
        this.message = message;
        this.fields = fields;
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

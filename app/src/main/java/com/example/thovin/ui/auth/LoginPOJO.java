package com.example.thovin.ui.auth;

public class LoginPOJO {

    private final String email;
    private final String password;

    public LoginPOJO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // --- Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

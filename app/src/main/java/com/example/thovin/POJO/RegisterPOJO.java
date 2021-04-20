package com.example.thovin.POJO;

import com.example.thovin.models.AddressModel;
import com.google.gson.annotations.SerializedName;

public class RegisterPOJO {
    @SerializedName("first_name")
    private final String firstName;
    @SerializedName("last_name")
    private final String lastName;
    private final String email;
    private final String password;
    private final String phone;
    private final AddressModel address;

    public RegisterPOJO(String firstName, String lastName, String email, String password, String phone, AddressModel address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    // --- Getters

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public AddressModel getAddress() {
        return address;
    }
}

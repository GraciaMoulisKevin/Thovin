package com.example.thovin.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thovin.models.AuthResponseModel;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<AuthResponseModel> user = new MutableLiveData<>();

    public void setUser(AuthResponseModel value) {
        user.setValue(value);
    }

    public LiveData<AuthResponseModel> getUser() {
        return user;
    }
}

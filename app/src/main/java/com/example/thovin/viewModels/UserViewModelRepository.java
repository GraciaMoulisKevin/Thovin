package com.example.thovin.viewModels;

import androidx.lifecycle.MutableLiveData;

import com.example.thovin.models.auth.AuthResult;

public class UserViewModelRepository {

    private static MutableLiveData<AuthResult> currentUser = new MutableLiveData<>();

    private static UserViewModelRepository INSTANCE;

    private UserViewModelRepository() { }

    public static UserViewModelRepository getInstance() {
        if (INSTANCE == null) INSTANCE = new UserViewModelRepository();
        return INSTANCE;
    }

    public MutableLiveData<AuthResult> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AuthResult value) {
        currentUser.setValue(value);
    }
}

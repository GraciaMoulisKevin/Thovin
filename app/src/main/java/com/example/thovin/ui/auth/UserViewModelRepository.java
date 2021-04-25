package com.example.thovin.ui.auth;

import androidx.lifecycle.MutableLiveData;

import com.example.thovin.results.AuthResult;

public class UserViewModelRepository {

    private static MutableLiveData<AuthResult> currentUser = new MutableLiveData<>();

    private static final UserViewModelRepository INSTANCE = new UserViewModelRepository();

    private UserViewModelRepository() { }

    public static UserViewModelRepository getInstance() {
        return INSTANCE;
    }

    public MutableLiveData<AuthResult> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AuthResult value) {
        currentUser.setValue(value);
    }
}

package com.example.thovin.ui.auth;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thovin.models.AddressModel;
import com.example.thovin.models.UserModel;
import com.example.thovin.services.HttpClient;
import com.example.thovin.POJO.auth.LoginPOJO;
import com.example.thovin.POJO.auth.RegisterPOJO;
import com.example.thovin.results.AuthResult;
import com.example.thovin.services.AuthServices;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {

    private static final UserViewModelRepository userViewModelRepository = UserViewModelRepository.getInstance();
    private static final AuthServices authServices = HttpClient.getInstance().getRetrofit().create(AuthServices.class);

    private MutableLiveData<AuthResult> currentUser;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);


    // --- GETTER && SETTERS
    public MutableLiveData<AuthResult> getCurrentUser() {
        if (currentUser == null) currentUser = userViewModelRepository.getCurrentUser();
        return currentUser;
    }

    public void setCurrentUser(AuthResult userValue) {
        userViewModelRepository.setCurrentUser(userValue);
        if (currentUser != null) currentUser.setValue(userValue);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    /**
     * Login
     */
    public void login(LoginPOJO loginPOJO) {

        setIsLoading(true);

        authServices.login(loginPOJO).enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                setIsLoading(false);

                AuthResult result = new AuthResult();

                if (response.isSuccessful()) {
                    result = response.body();
                    result.setSuccess(true);
                } else {
                    try {
                        Gson gson = new Gson();
                        result = gson.fromJson(response.errorBody().string(), AuthResult.class);
                        result.setSuccess(false);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                result.setType(AuthResult.LOGIN);
                result.setResCode(response.code());
                setCurrentUser(result);
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                setIsLoading(false);
                setCurrentUser(new AuthResult(0, -1));
            }
        });
//        setCurrentUser(getMockUser());
    }

    /**
     * Register
     */
    public void register(RegisterPOJO registerPOJO) {

        setIsLoading(true);

        authServices.register(registerPOJO).enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                setIsLoading(false);

                AuthResult result = new AuthResult();

                if (response.isSuccessful()) {
                    result = response.body();
                    result.setSuccess(true);
                } else {
                    try {
                        Gson gson = new Gson();
                        result = gson.fromJson(response.errorBody().string(), AuthResult.class);
                        result.setSuccess(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                result.setResCode(response.code());
                result.setType(AuthResult.REGISTER);
                currentUser.setValue(result);
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                setIsLoading(false);
                currentUser.setValue(new AuthResult(1, -1));
            }
        });
    }

    /**
     * Logout
     */
    public void logout() {
        setCurrentUser(null);
    }


//    public AuthResult getMockUser() {
//        UserModel user = new UserModel();
//        user.setAdmin(false);
//        user.setLastName("boop");
//        user.setFirstName("boop");
//        user.setEmail("boop@gmail.com");
//        user.setPhone("0600000000");
//        user.setType("client");
//
//        AddressModel address = new AddressModel();
//        address.setStreet("1 rue des amandiers");
//        address.setAdditional("");
//        address.setCity("Montpellier");
//        address.setZip("34000");
//        address.setCountry("France");
//        user.setAddress(address);
//
//        AuthResult result = new AuthResult();
//        result.resCode = 200;
//        result.success = true;
//        result.setUser(user);
//        result.setType(AuthResult.LOGIN);
//
//        setIsLoading(false);
//        return result;
//    }
}
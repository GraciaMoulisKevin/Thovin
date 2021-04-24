package com.example.thovin.ui.auth;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thovin.HttpClient;
import com.example.thovin.models.AuthResult;
import com.example.thovin.services.AuthServices;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserViewModel extends ViewModel {

    private final Retrofit retrofit = new HttpClient(HttpClient.DEBUG_ON).getRetrofit();
    private final AuthServices authServices = retrofit.create(AuthServices.class);

    private MutableLiveData<AuthResult> user = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public MutableLiveData<AuthResult> getUser() {
        return user;
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

                result.setType(0);
                result.setResCode(response.code());
                user.setValue(result);
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                setIsLoading(false);
                user.setValue(new AuthResult(0, -1));
            }
        });
    }

    /**
     * Login
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
                result.setType(1);
                user.setValue(result);
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                setIsLoading(false);
                user.setValue(new AuthResult(1, -1));
            }
        });
    }
}

package com.example.thovin.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thovin.HttpClient;
import com.example.thovin.MainActivity;
import com.example.thovin.models.AuthResult;
import com.example.thovin.repositories.AuthRepository;
import com.example.thovin.services.AuthServices;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserViewModel extends ViewModel {

    private final Retrofit retrofit = new HttpClient(HttpClient.DEBUG_ON).getRetrofit();
    private final AuthServices authServices = retrofit.create(AuthServices.class);

    private MutableLiveData<AuthResult> user = new MutableLiveData<>();

    public MutableLiveData<AuthResult> getUser() {
        return user;
    }

    public void setUser(AuthResult value) {
        user.setValue(value);
    }

    /**
     * Login
     */
    public MutableLiveData<AuthResult> login(LoginPOJO loginPOJO) {

        authServices.login(loginPOJO).enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                if (response.isSuccessful()) {
                    AuthResult result = response.body();
                    result.setSuccess(true);
                    user.setValue(result);
                } else {
                    try {
                        Gson gson = new Gson();
                        AuthResult result = gson.fromJson(response.errorBody().string(), AuthResult.class);
                        result.setResCode(response.code());
                        result.setSuccess(false);
                        user.setValue(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                user.setValue(new AuthResult(-1));
            }
        });

        return user;
    }

    /**
     * Login
     */
    public MutableLiveData<AuthResult> register(RegisterPOJO registerPOJO) {

        authServices.register(registerPOJO).enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                if (response.isSuccessful()) {
                    AuthResult result = response.body();
                    result.setSuccess(true);
                    user.setValue(result);
                } else {
                    try {
                        Gson gson = new Gson();
                        AuthResult result = gson.fromJson(response.errorBody().string(), AuthResult.class);
                        result.setResCode(response.code());
                        result.setSuccess(false);
                        user.setValue(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                user.setValue(new AuthResult(-1));
            }
        });

        return user;
    }
}

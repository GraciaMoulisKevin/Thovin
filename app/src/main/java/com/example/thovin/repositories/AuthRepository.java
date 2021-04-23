package com.example.thovin.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.thovin.HttpClient;
import com.example.thovin.models.AuthResult;
import com.example.thovin.services.AuthServices;
import com.example.thovin.ui.auth.LoginPOJO;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthRepository {

    private final AuthServices authServices;
    private MutableLiveData<AuthResult> authResult = new MutableLiveData<>();

    private static final AuthRepository instance = new AuthRepository();

    public AuthRepository() {
        Retrofit retrofit = new HttpClient(HttpClient.DEBUG_ON).getRetrofit();
        authServices = retrofit.create(AuthServices.class);
    }

    public static AuthRepository getInstance() {
        return instance;
    }

    public MutableLiveData<AuthResult> login(LoginPOJO login) {
        authServices.login(login).enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Call<AuthResult> call, Response<AuthResult> response) {
                if (response.isSuccessful()) {
                    AuthResult result = response.body();
                    result.setSuccess(true);
                    authResult.setValue(result);
                } else {
                    try {
                        Gson gson = new Gson();
                        AuthResult result = gson.fromJson(response.errorBody().string(), AuthResult.class);
                        result.setResCode(response.code());
                        result.setSuccess(false);
                        authResult.setValue(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResult> call, Throwable t) {
                authResult.setValue(new AuthResult(-1));
            }
        });

        return authResult;
    }
}

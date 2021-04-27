package com.example.thovin.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thovin.Utility;
import com.example.thovin.models.user.UserModel;
import com.example.thovin.services.HttpClient;
import com.example.thovin.services.RestaurantServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantViewModel extends ViewModel {
    private static final RestaurantServices restaurantServices = HttpClient.getInstance().getRestaurantServices();

    private MutableLiveData<ArrayList<UserModel>> restaurants;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private int state = -1;

    // --- GETTER && SETTERS
    public MutableLiveData<ArrayList<UserModel>> getRestaurants() {
        if (restaurants == null) restaurants = new MutableLiveData<>();
        return restaurants;
    }

    public void setRestaurants(ArrayList<UserModel> value) {
        restaurants.setValue(value);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void loadRestaurant(String token) {
        setIsLoading(true);

        restaurantServices.getRestaurants("Bearer " + token).enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                setIsLoading(false);
                if (response.isSuccessful()) {
                    setState(Utility.STATE_SUCCESS);
                    setRestaurants(response.body());
                }
                else {
                    setState(Utility.STATE_ERROR);
                    setRestaurants(null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                setIsLoading(false);
                setState(Utility.STATE_FAILURE);
                setRestaurants(null);
            }
        });
    }
}

package com.example.thovin.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thovin.Utility;
import com.example.thovin.models.UserModel;
import com.example.thovin.services.HttpClient;
import com.example.thovin.services.IRestaurantServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantViewModel extends ViewModel {
    private static final IRestaurantServices apiRestaurantServices = HttpClient.getInstance().getRestaurantServices();

    /**
     * The list of all available restaurant
     */
    private MutableLiveData<ArrayList<UserModel>> restaurants;

    /**
     * The current restaurant the user is watching
     */
    private MutableLiveData<UserModel> currentRestaurant;

    /**
     * A boolean to inspect loading progression
     */
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private int state = -1;

    // ---------------------------------------------------------------------------------------------
    // --- GETTER && SETTERS
    public MutableLiveData<ArrayList<UserModel>> getRestaurants() {
        if (restaurants == null) restaurants = new MutableLiveData<>();
        return restaurants;
    }

    public void setRestaurants(ArrayList<UserModel> value) {
        restaurants.setValue(value);
    }

    public MutableLiveData<UserModel> getCurrentRestaurant() {
        if (currentRestaurant == null) currentRestaurant = new MutableLiveData<>();
        return currentRestaurant;
    }

    public void setCurrentRestaurant(UserModel value) {
        if (currentRestaurant == null) currentRestaurant = new MutableLiveData<>();
        currentRestaurant.setValue(value);
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
    // ---------------------------------------------------------------------------------------------

    /**
     * Load all the restaurants available from the database
     * @param token The user authorization token
     */
    public void loadRestaurant(String token) {
        setIsLoading(true);

        apiRestaurantServices.getRestaurants("Bearer " + token).enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {

                if (response.isSuccessful()) {
                    setState(Utility.STATE_SUCCESS);
                    setRestaurants(response.body());
                }
                else {
                    setState(Utility.STATE_ERROR);
                    setRestaurants(null);
                }

                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                setState(Utility.STATE_FAILURE);
                setRestaurants(null);
                setIsLoading(false);
            }
        });
    }
}

package com.example.thovin.viewModels;

import android.text.BoringLayout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thovin.Utility;
import com.example.thovin.models.MenuModel;
import com.example.thovin.models.ProductModel;
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
     * The current restaurant menus
     */
    private MutableLiveData<ArrayList<MenuModel>> currentRestaurantMenus;

    /**
     * The current restaurant products
     */
    private MutableLiveData<ArrayList<ProductModel>> currentRestaurantProducts;

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

    public MutableLiveData<ArrayList<MenuModel>> getCurrentRestaurantMenus() {
        if (currentRestaurantMenus == null) currentRestaurantMenus = new MutableLiveData<>();
        return currentRestaurantMenus;
    }

    public void setCurrentRestaurantMenus(ArrayList<MenuModel> value) {
        if (currentRestaurantMenus == null) currentRestaurantMenus = new MutableLiveData<>();
        currentRestaurantMenus.setValue(value);
    }

    public MutableLiveData<ArrayList<ProductModel>> getCurrentRestaurantProducts() {
        if (currentRestaurantProducts == null) currentRestaurantProducts = new MutableLiveData<>();
        return currentRestaurantProducts;
    }

    public void setCurrentRestaurantProducts(ArrayList<ProductModel> value) {
        if (currentRestaurantProducts == null) currentRestaurantProducts = new MutableLiveData<>();
        currentRestaurantProducts.setValue(value);
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

    /**
     * Load all the menus available from the database
     * @param token The user authorization token
     */
    public void getMenus(String token, String restaurantId) {
        setIsLoading(true);

        apiRestaurantServices.getMenus("Bearer " + token, restaurantId).enqueue(new Callback<ArrayList<MenuModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MenuModel>> call, Response<ArrayList<MenuModel>> response) {

                if (response.isSuccessful()) setCurrentRestaurantMenus(response.body());
                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<ArrayList<MenuModel>> call, Throwable t) {
                setCurrentRestaurantMenus(null);
                setIsLoading(false);
            }
        });
    }

    /**
     * Load all the menus available from the database
     * @param token The user authorization token
     */
    public void getProducts(String token, String restaurantId) {
        setIsLoading(true);

        apiRestaurantServices.getProducts("Bearer " + token, restaurantId).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {

                if (response.isSuccessful()) setCurrentRestaurantProducts(response.body());
                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                setCurrentRestaurantProducts(null);
                setIsLoading(false);
            }
        });
    }

    /**
     * Add a new product in the restaurant
     * @param token The user authorization token
     * @param restaurantId The restaurant id
     * @param product The product to add
     */
    public void addNewProduct(String token, String restaurantId, ProductModel product) {
        setIsLoading(true);

        apiRestaurantServices.postProduct("Bearer " + token, restaurantId, product).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {

                if (response.isSuccessful()) {
                    setState(Utility.STATE_SUCCESS);
                    setCurrentRestaurantProducts(response.body());
                }
                else setState(Utility.STATE_ERROR);
                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                setState(Utility.STATE_FAILURE);
                setIsLoading(false);
            }
        });
    }

    /**
     * Remove a product of the restaurant
     * @param token The user authorization token
     * @param restaurantId The restaurant id
     * @param productId The product id to remove
     */
    public void removeProduct(String token, String restaurantId, String productId) {
        setIsLoading(true);

        apiRestaurantServices.deleteProduct("Bearer " + token, restaurantId, productId).enqueue(new Callback<ArrayList<ProductModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {

                if (response.isSuccessful()) {
                    setState(Utility.STATE_SUCCESS);
                    setCurrentRestaurantProducts(response.body());
                }
                else setState(Utility.STATE_ERROR);
                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                setState(Utility.STATE_FAILURE);
                setIsLoading(false);
            }
        });
    }
}

package com.example.thovin.viewModels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thovin.Utility;
import com.example.thovin.models.AddItemRequest;
import com.example.thovin.models.AuthResult;
import com.example.thovin.models.CartModel;
import com.example.thovin.models.ErrorModel;
import com.example.thovin.models.MenuModel;
import com.example.thovin.services.HttpClient;
import com.example.thovin.services.ICartServices;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {
    private static final ICartServices apiCartServices = HttpClient.getInstance().getCartServices();

    /**
     * Current user cart.
     */
    private MutableLiveData<CartModel> currentCart;

    /**
     * A boolean to inspect loading progression.
     */
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    /**
     * Handle error response.
     */
    private MutableLiveData<ErrorModel> err = new MutableLiveData<>();


    // ---------------------------------------------------------------------------------------------
    // --- GETTER && SETTERS
    public MutableLiveData<CartModel> getCurrentCart() {
        if (currentCart == null) currentCart = new MutableLiveData<>();
        return currentCart;
    }

    public void setCurrentCart(CartModel value) {
        if (currentCart == null) currentCart = new MutableLiveData<>();
        this.currentCart.setValue(value);
    }


    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public MutableLiveData<ErrorModel> getErr() {
        return err;
    }

    public void setErr(ErrorModel err) {
        this.err.setValue(err);
    }

    // ---------------------------------------------------------------------------------------------

    /**
     * Initialize user cart
     *
     * @param token the user authorization token
     */
    public void initCart(String token) {
        setIsLoading(true);

        apiCartServices.getUserCart("Bearer " + token).enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {

                if (response.isSuccessful()) setCurrentCart(response.body());
                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
                setIsLoading(false);
            }
        });
    }

    /**
     * Add new item in the cart
     *
     * @param token        the user authorization token
     * @param menuId       the menu id to add
     * @param restaurantId the restaurant id providing the menu
     */
    public void addItem(String token, String menuId, String restaurantId) {
        setIsLoading(true);

        AddItemRequest itemRequest = new AddItemRequest(menuId, restaurantId);

        apiCartServices.addItem("Bearer " + token, itemRequest).enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {

                if (response.isSuccessful()) {
                    setCurrentCart(response.body());
                } else {
                    try {
                        setErr(Utility.GSON.fromJson(response.errorBody().string(), ErrorModel.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
                setErr(new ErrorModel(-1));
                setIsLoading(false);
            }
        });
    }

    /**
     * Empty the cart
     *
     * @param token the user authorization token
     */
    public void deleteCart(String token) {
        setIsLoading(true);

        apiCartServices.deleteCart("Bearer " + token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) setCurrentCart(null);
                else {
                    try {
                        setErr(Utility.GSON.fromJson(response.errorBody().string(), ErrorModel.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                setErr(new ErrorModel(-1));
                setIsLoading(false);
            }
        });
    }
}

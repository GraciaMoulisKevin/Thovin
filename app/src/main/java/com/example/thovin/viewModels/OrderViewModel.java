package com.example.thovin.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thovin.models.AuthResult;
import com.example.thovin.models.OrderRequest;
import com.example.thovin.models.OrderResult;
import com.example.thovin.services.HttpClient;
import com.example.thovin.services.IAuthServices;
import com.example.thovin.services.IOrderServices;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderViewModel extends ViewModel {

    /**
     * Retrofit services
     */
    private static final IOrderServices apiOrderServices = HttpClient.getInstance().getOrderServices();

    /**
     * The current user connected
     */
    private MutableLiveData<OrderResult> currentOrder;

    /**
     * The order historic
     */
    private MutableLiveData<ArrayList<OrderResult>> historic;

    /**
     * A boolean to inspect loading progression
     */
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);


    // ---------------------------------------------------------------------------------------------
    // --- GETTER && SETTERS
    public MutableLiveData<OrderResult> getCurrentOrder() {
        if (currentOrder == null) currentOrder = new MutableLiveData<>();
        return currentOrder;
    }

    public void setCurrentOrder(OrderResult order) {
        if (currentOrder == null) currentOrder = new MutableLiveData<>();
        currentOrder.setValue(order);
    }


    public MutableLiveData<ArrayList<OrderResult>> getHistoric() {
        if (historic == null) historic = new MutableLiveData<>();
        return historic;
    }

    public void setHistoric(ArrayList<OrderResult> _historic) {
        historic.setValue(_historic);
    }


    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean value) {
        isLoading.setValue(value);
    }
    // ---------------------------------------------------------------------------------------------


    /**
     * Retrieve all user orders
     *
     * @param authorizationToken The user authorization token
     */
    public void getOrders(String authorizationToken) {
        setIsLoading(true);

        apiOrderServices.getOrder("Bearer " + authorizationToken).enqueue(new Callback<ArrayList<OrderResult>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderResult>> call, Response<ArrayList<OrderResult>> response) {

                if (response.isSuccessful()) {
                    setHistoric(response.body());
                } else {
                    setHistoric(null);
                }

                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<ArrayList<OrderResult>> call, Throwable t) {
                setHistoric(null);
                setIsLoading(false);
            }
        });
    }

    /**
     * Post a new order after user pay for it
     *
     * @param authorizationToken The user authorization token
     * @param orderRequest       The order requested
     */
    public void postOrder(String authorizationToken, OrderRequest orderRequest) {
        setIsLoading(true);

        apiOrderServices.postOrder("Bearer " + authorizationToken, orderRequest).enqueue(new Callback<OrderResult>() {
            @Override
            public void onResponse(Call<OrderResult> call, Response<OrderResult> response) {

                if (response.isSuccessful()) {
                    setCurrentOrder(response.body());
                }

                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<OrderResult> call, Throwable t) {
                setIsLoading(false);
            }

        });
    }
}
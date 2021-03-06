package com.example.thovin.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thovin.Utility;
import com.example.thovin.models.ErrorModel;
import com.example.thovin.models.OrderRequest;
import com.example.thovin.models.OrderResult;
import com.example.thovin.services.HttpClient;
import com.example.thovin.services.IOrderServices;

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
    private MutableLiveData<ArrayList<OrderResult>> currentOrders;

    /**
     * The order historic
     */
    private MutableLiveData<ArrayList<OrderResult>> historic;

    /**
     * A boolean to inspect loading progression
     */
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    /**
     * A boolean to inspect loading progression
     */
    private MutableLiveData<Boolean> isPaymentSuccess;

    /**
     * Handle error response.
     */
    private MutableLiveData<ErrorModel> err = new MutableLiveData<>();

    // ---------------------------------------------------------------------------------------------
    // --- GETTER && SETTERS
    public MutableLiveData<ArrayList<OrderResult>> getCurrentOrders() {
        if (currentOrders == null) currentOrders = new MutableLiveData<>();
        return currentOrders;
    }

    public void setCurrentOrders(ArrayList<OrderResult> order) {
        if (currentOrders == null) currentOrders = new MutableLiveData<>();
        currentOrders.setValue(order);
    }


    public MutableLiveData<ArrayList<OrderResult>> getHistoric() {
        if (historic == null) historic = new MutableLiveData<>();
        return historic;
    }

    public void setHistoric(ArrayList<OrderResult> _historic) {
        if (historic == null) historic = new MutableLiveData<>();
        historic.setValue(_historic);
    }


    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean value) {
        isLoading.setValue(value);
    }


    public MutableLiveData<Boolean> getIsPaymentSuccess() {
        if (isPaymentSuccess == null) isPaymentSuccess = new MutableLiveData<>();
        return isPaymentSuccess;
    }

    public void setIsPaymentSuccess(Boolean value) {
        isPaymentSuccess.setValue(value);
    }

    public void dumpIsPaymentSuccess() {
        isPaymentSuccess = null;
    }

    public MutableLiveData<ErrorModel> getErr() {
        return err;
    }

    public void setErr(ErrorModel err) {
        this.err.setValue(err);
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
                    ArrayList<OrderResult> orders = response.body();
                    ArrayList<OrderResult> historic = new ArrayList<>();
                    ArrayList<OrderResult> pendingOrders = new ArrayList<>();

                    for (OrderResult order : orders) {
                        if (order.status.equals("delivered"))
                            historic.add(order);
                        else
                            pendingOrders.add(order);
                    }

                    setCurrentOrders(pendingOrders);
                    setHistoric(historic);

                } else {
                    setHistoric(null);

                    try {
                        setErr(Utility.GSON.fromJson(response.errorBody().string(), ErrorModel.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<ArrayList<OrderResult>> call, Throwable t) {
                setErr(new ErrorModel(-1));
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
                    ArrayList<OrderResult> orders = currentOrders.getValue();
                    orders.add(response.body());
                    setCurrentOrders(orders);
                    setIsPaymentSuccess(true);
                }
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
            public void onFailure(Call<OrderResult> call, Throwable t) {
                setErr(new ErrorModel(-1));
                setIsLoading(false);
            }

        });
    }

    /**
     * Post a new order after user pay for it
     *
     * @param authorizationToken The user authorization token
     * @param orderId            The order id
     */
    public void updateStatus(String authorizationToken, String orderId) {
        setIsLoading(true);

        apiOrderServices.updateStatus("Bearer " + authorizationToken, orderId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    ArrayList<OrderResult> orders = currentOrders.getValue();
                    if (response.body().equals("delivered")) orders.remove(orders.size() - 1);
                    else orders.get(orders.size() - 1).status = response.body();

                    setCurrentOrders(orders);
                }
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
            public void onFailure(Call<String> call, Throwable t) {
                setErr(new ErrorModel(-1));
                setIsLoading(false);
            }
        });
    }
}
package com.example.thovin.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thovin.models.AddItemRequest;
import com.example.thovin.models.CartModel;
import com.example.thovin.services.HttpClient;
import com.example.thovin.services.ICartServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {
    private static final ICartServices apiCartServices = HttpClient.getInstance().getCartServices();

    /**
     * Current user cart
     */
    private MutableLiveData<CartModel> currentCart = new MutableLiveData<>();

    /**
     * A boolean to inspect loading progression
     */
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);


    // ---------------------------------------------------------------------------------------------
    // --- GETTER && SETTERS
    public MutableLiveData<CartModel> getCurrentCart() {
        if (currentCart == null) currentCart = new MutableLiveData<>();
        return currentCart;
    }

    public void setCurrentCart(CartModel currentCart) { this.currentCart.setValue(currentCart); }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }
    // ---------------------------------------------------------------------------------------------

    public void initCart(String token) {
        setIsLoading(true);

        apiCartServices.getUserCart("Bearer " + token).enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {

                if (response.isSuccessful()) {
                    setCurrentCart(response.body());
                }
                else {
                    // error
                }

                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
                setIsLoading(false);
            }
        });
    }

    public void addItem(String token, String menuId, String restaurantId) {
        setIsLoading(true);

        AddItemRequest itemRequest = new AddItemRequest(menuId, restaurantId);

        apiCartServices.addItem("Bearer " + token, itemRequest).enqueue(new Callback<CartModel>() {
            @Override
            public void onResponse(Call<CartModel> call, Response<CartModel> response) {

                if (response.isSuccessful()) {
                    currentCart.setValue(response.body());
                }
                else {
                    // error
                }

                setIsLoading(false);
            }

            @Override
            public void onFailure(Call<CartModel> call, Throwable t) {
                setIsLoading(false);
            }
        });
    }

    //    public double totalPrice() {
//        double total = 0;
//        ArrayList<MenuModel> menus = new ArrayList<>(); // currentCart.getValue().getMenus();
//
//        for (MenuModel menu : menus) {
//            for (ProductModel product : menu.getProducts()) {
//                total += product.price();
//            }
//        }
//
//        return total;
//    }
}

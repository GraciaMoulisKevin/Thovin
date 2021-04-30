package com.example.thovin.viewModels;

import androidx.lifecycle.MutableLiveData;

import com.example.thovin.models.CartModel;
import com.example.thovin.models.MenuModel;
import com.example.thovin.models.ProductModel;
import com.example.thovin.services.HttpClient;
import com.example.thovin.services.ICartServices;

import java.util.ArrayList;

public class CartViewModel {
    private static final ICartServices apiCartServices = HttpClient.getInstance().getCartServices();

    /**
     * Current user cart
     */
    private MutableLiveData<CartModel> currentCart;

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

    public void setCurrentCart(CartModel currentCart) {
        this.currentCart.setValue(currentCart);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }
    // ---------------------------------------------------------------------------------------------

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

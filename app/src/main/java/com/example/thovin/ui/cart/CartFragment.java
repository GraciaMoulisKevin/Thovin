package com.example.thovin.ui.cart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.thovin.R;
import com.example.thovin.adapters.ProductAdapter;
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.AuthResult;
import com.example.thovin.models.CartModel;
import com.example.thovin.viewModels.CartViewModel;
import com.example.thovin.viewModels.UserViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class CartFragment extends Fragment implements RecycleViewOnClickListener {

    private View rootView;
    private Context context;

    // --- User
    private UserViewModel userViewModel;
    private AuthResult user;
    private CartViewModel cartViewModel;
    private CartModel currentCart;

    private RecyclerView products;

    public CartFragment() {
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        context = getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureViews();
        configureViewModels();

        user = userViewModel.getCurrentUser().getValue();

        cartViewModel.getCurrentCart().observe(getViewLifecycleOwner(), currentCart -> {
            if (currentCart != null) {
                this.currentCart = currentCart;
                setProductsRecyclerView();
            }
        });
    }

    /**
     * Configure simple Views
     */
    public void configureViews() {
        products = rootView.findViewById(R.id.menu_products);
    }

    /**
     * Configure all View Models
     */
    public void configureViewModels() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
    }

    /**
     * Set products recycler view
     */
    public void setProductsRecyclerView() {
        ArrayList<String> items = currentCart.getMenus();
        Log.i("DEBUG", items.toString());
        products.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ProductAdapter productAdapter = new ProductAdapter(context, items, this);
        products.setAdapter(productAdapter);
    }

    @Override
    public void onItemClick(int position) {
    }
}
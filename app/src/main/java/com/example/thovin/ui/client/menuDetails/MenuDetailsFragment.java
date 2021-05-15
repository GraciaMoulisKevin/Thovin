package com.example.thovin.ui.client.menuDetails;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.adapters.ProductAdapter;
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.AuthResult;
import com.example.thovin.models.UserModel;
import com.example.thovin.viewModels.CartViewModel;
import com.example.thovin.viewModels.RestaurantViewModel;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuDetailsFragment extends Fragment implements RecycleViewOnClickListener {

    private View rootView;
    private Context context;
    private TextView name, price;
    private RecyclerView products;
    private int argsPosition;

    // --- User
    private UserViewModel userViewModel;
    private AuthResult user;
    private CartViewModel cartViewModel;

    // --- Restaurant
    private RestaurantViewModel restaurantViewModel;
    private UserModel currentRestaurant;
    private String currentMenu;


    public MenuDetailsFragment() {
    }

    public static MenuDetailsFragment newInstance() {
        return new MenuDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_menu_details, container, false);
        context = getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        argsPosition = 0;

        configureViews();
        configureViewModels();

        user = userViewModel.getCurrentUser().getValue();
        restaurantViewModel.getCurrentRestaurant().observe(getViewLifecycleOwner(), currentRestaurant -> {
            if (currentRestaurant != null) {
                this.currentRestaurant = currentRestaurant;
                Utility.getSuccessSnackbar(getContext(), view, currentRestaurant.restaurantName, Snackbar.LENGTH_SHORT);
                currentMenu = currentRestaurant.getMenusId().get(argsPosition);
                setViews();
            }
        });


        Button addToCartBtn = rootView.findViewById(R.id.add_to_cart_btn);
        addToCartBtn.setOnClickListener(v -> cartViewModel.addItem(user.token, currentMenu, currentRestaurant.id));
    }

    /**
     * Configure all View Models
     */
    public void configureViewModels() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
    }


    public void configureViews() {
        name = rootView.findViewById(R.id.menu_name);
        price = rootView.findViewById(R.id.menu_price);

        products = rootView.findViewById(R.id.menu_products);
    }

    public void setViews() {
        name.setText(currentMenu);
        price.setText(currentMenu);
        setProductsRecyclerView();
    }

    public void setProductsRecyclerView() {
        ArrayList<String> currentMenu_products = new ArrayList<>(Arrays.asList("Product1", "Product2"));
        products.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ProductAdapter productAdapter = new ProductAdapter(context, currentMenu_products, this);
        products.setAdapter(productAdapter);
    }

    @Override
    public void onItemClick(int position) {
        // Navigation.findNavController(rootView).navigate();
    }
}
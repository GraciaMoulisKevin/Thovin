package com.example.thovin.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.adapters.MenuAdapter;
import com.example.thovin.adapters.ProductModelAdapter;
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.AuthResult;
import com.example.thovin.models.MenuModel;
import com.example.thovin.models.ProductModel;
import com.example.thovin.models.UserModel;
import com.example.thovin.viewModels.RestaurantViewModel;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class RestaurantHomeFragment extends Fragment implements RecycleViewOnClickListener {

    private View rootView;
    private Context context;

    // --- User view model
    private UserViewModel userViewModel;
    private AuthResult currentUser;

    private RestaurantViewModel restaurantViewModel;
    private UserModel currentRestaurant;

    private TextView name;
    private RecyclerView menusRecyclerView;
    private RecyclerView productsRecyclerView;

    private boolean isRestaurantOwner = false;
    public RestaurantHomeFragment() { }

    public static RestaurantHomeFragment newInstance() {
        return new RestaurantHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_restaurant_home, container, false);
        context = getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureViews();
        configureViewModel();

        currentUser = userViewModel.getCurrentUser().getValue();
        currentRestaurant = restaurantViewModel.getCurrentRestaurant().getValue();

        // --- Mean user connected is the restaurant owner
        if (currentRestaurant == null) {
            isRestaurantOwner = true;
            restaurantViewModel.setCurrentRestaurant(currentUser.user);
            rootView.findViewById(R.id.restaurant_products).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.add_menu_btn).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.products_header).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.add_product_btn).setVisibility(View.VISIBLE);
        }

        restaurantViewModel.getCurrentRestaurant().observe(getViewLifecycleOwner(), currentRestaurant -> {
            if (currentRestaurant != null) {
                Utility.getSuccessSnackbar(getContext(), view, currentRestaurant.restaurantName, Snackbar.LENGTH_SHORT);
                this.currentRestaurant = currentRestaurant;

                name.setText(currentRestaurant.getRestaurantName());

                restaurantViewModel.getMenus(currentUser.token, currentRestaurant.id);

                if (isRestaurantOwner)
                    restaurantViewModel.getProducts(currentUser.token, currentRestaurant.id);
            }
        });

        restaurantViewModel.getCurrentRestaurantMenus().observe(getViewLifecycleOwner(), this::setMenusRecyclerView);

        if (isRestaurantOwner)
            restaurantViewModel.getCurrentRestaurantProducts().observe(getViewLifecycleOwner(), this::setProductsRecyclerView);

    }

    public void configureViews() {
        name = rootView.findViewById(R.id.restaurant_name);
        menusRecyclerView = rootView.findViewById(R.id.restaurant_menus);
        productsRecyclerView = rootView.findViewById(R.id.restaurant_products);
    }

    public void configureViewModel() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
    }

    public void setMenusRecyclerView(ArrayList<MenuModel> menus) {
        if (menus != null && menus.size() > 0) {
            menusRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            MenuAdapter menuAdapter = new MenuAdapter(context, menus, this);
            menusRecyclerView.setAdapter(menuAdapter);
        }
    }

    public void setProductsRecyclerView(ArrayList<ProductModel> products) {
        if (products != null && products.size() > 0) {
            productsRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            ProductModelAdapter menuAdapter = new ProductModelAdapter(context, products, this);
            productsRecyclerView.setAdapter(menuAdapter);
        }
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        Navigation.findNavController(rootView).navigate(R.id.action_nav_restaurant_details_to_nav_menu_details, bundle);
    }
}
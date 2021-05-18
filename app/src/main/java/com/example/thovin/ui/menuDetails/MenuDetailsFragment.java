package com.example.thovin.ui.menuDetails;

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
import com.example.thovin.models.MenuModel;
import com.example.thovin.models.ProductResult;
import com.example.thovin.models.UserModel;
import com.example.thovin.viewModels.CartViewModel;
import com.example.thovin.viewModels.RestaurantViewModel;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MenuDetailsFragment extends Fragment implements RecycleViewOnClickListener {

    private View rootView;
    private Context context;
    private TextView name, price;
    private RecyclerView products;
    private int menuPosition;

    // --- User
    private UserViewModel userViewModel;
    private AuthResult user;
    private CartViewModel cartViewModel;

    // --- Restaurant
    private RestaurantViewModel restaurantViewModel;
    private UserModel currentRestaurant;
    private MenuModel currentMenu;


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
        menuPosition = getArguments().getInt("POSITION");

        configureViews();
        configureViewModels();

        user = userViewModel.getCurrentUser().getValue();
        currentRestaurant = restaurantViewModel.getCurrentRestaurant().getValue();

        if (currentRestaurant != null)
            currentMenu = restaurantViewModel.getCurrentRestaurantMenus().getValue().get(menuPosition);

        name.setText(currentMenu.name);
        price.setText(String.valueOf(getTotalPrice(currentMenu.products)));
        setProductsRecyclerView();

        Button addToCartBtn = rootView.findViewById(R.id.add_to_cart_btn);
        addToCartBtn.setOnClickListener(v -> cartViewModel.addItem(user.token, currentMenu.id, currentRestaurant.id));
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

    public void setProductsRecyclerView() {
        products.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ProductAdapter productAdapter = new ProductAdapter(context, currentMenu.products, this, false);
        products.setAdapter(productAdapter);
    }

    public float getTotalPrice(ArrayList<ProductResult> products) {
        float total = 0;
        for (ProductResult p : products) total += p.price;
        return total;
    }

    @Override
    public void onItemClick(int position) {
        // Navigation.findNavController(rootView).navigate();
    }
}
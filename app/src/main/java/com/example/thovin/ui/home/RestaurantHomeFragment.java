package com.example.thovin.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

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
    private MaterialButton addProductBtn, addMenuBtn;

    private RecyclerView menusRecyclerView;
    private RecyclerView productsRecyclerView;

    private Boolean isFirstStart = true;

    public RestaurantHomeFragment() {
    }

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

        if (isFirstStart) {
            // --- Welcome message
            Utility.getSuccessSnackbar(context, view, getString(R.string.success_connection),
                    Snackbar.LENGTH_SHORT).show();

            // --- Set current restaurant
            restaurantViewModel.setCurrentRestaurant(currentRestaurant);

            // --- Load menus
            restaurantViewModel.getMenus(currentUser.token, currentRestaurant.id);

            // --- Load products
            restaurantViewModel.getProducts(currentUser.token, currentRestaurant.id);

            isFirstStart = false;
        }

        name.setText(currentRestaurant.restaurantName);

        // --- Show and observe menus
        restaurantViewModel.getCurrentRestaurantMenus().observe(getViewLifecycleOwner(), this::setMenusRecyclerView);

        // --- Show and observe products
        restaurantViewModel.getCurrentRestaurantProducts().observe(getViewLifecycleOwner(), this::setProductsRecyclerView);
    }

    public void configureViews() {
        name = rootView.findViewById(R.id.restaurant_name);
        menusRecyclerView = rootView.findViewById(R.id.restaurant_menus);

        rootView.findViewById(R.id.products_header).setVisibility(View.VISIBLE);
        productsRecyclerView = rootView.findViewById(R.id.restaurant_products);
        productsRecyclerView.setVisibility(View.VISIBLE);

        addMenuBtn = rootView.findViewById(R.id.add_menu_btn);
        addMenuBtn.setVisibility(View.VISIBLE);

        addProductBtn = rootView.findViewById(R.id.add_product_btn);
        addProductBtn.setVisibility(View.VISIBLE);

        addProductBtn.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_nav_home_to_nav_product_editor));

    }

    public void configureViewModel() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        currentUser = userViewModel.getCurrentUser().getValue();
        if (currentUser == null) startActivity(Utility.getLogoutIntent(context));
        else currentRestaurant = currentUser.user;

        restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
        restaurantViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
                Utility.toggleSpinner(getActivity(), isLoading));
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
    public void onItemClick(int position, String tag) {
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        if (tag.equals(RecycleViewOnClickListener.TAG_PRODUCT))
            Navigation.findNavController(rootView).navigate(R.id.action_nav_home_to_nav_product_editor, bundle);
    }
}
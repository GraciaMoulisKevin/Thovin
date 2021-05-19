package com.example.thovin.ui.restaurantDetails;

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

public class RestaurantDetailsFragment extends Fragment implements RecycleViewOnClickListener {

    private View rootView;
    private Context context;

    // --- User view model
    private UserViewModel userViewModel;
    private AuthResult currentUser;

    private RestaurantViewModel restaurantViewModel;
    private UserModel currentRestaurant;

    private TextView name;

    private RecyclerView menusRecyclerView;

    public RestaurantDetailsFragment() {
    }

    public static RestaurantDetailsFragment newInstance() {
        return new RestaurantDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_restaurant_details, container, false);
        context = getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureViews();
        configureViewModel();

        // --- Check current user
        currentUser = userViewModel.getCurrentUser().getValue();
        if (currentUser == null) startActivity(Utility.getLogoutIntent(context));

        // --- Check current restaurant
        currentRestaurant = restaurantViewModel.getCurrentRestaurant().getValue();
        if (currentRestaurant == null) Navigation.findNavController(view).popBackStack();

        // --- Set restaurant name
        name.setText(currentRestaurant.restaurantName);

        // --- Get and observe menus
        restaurantViewModel.getMenus(currentUser.token, currentRestaurant.id);
        restaurantViewModel.getCurrentRestaurantMenus().observe(getViewLifecycleOwner(), this::setMenusRecyclerView);
    }

    public void configureViews() {
        name = rootView.findViewById(R.id.restaurant_name);
        menusRecyclerView = rootView.findViewById(R.id.restaurant_menus);
    }

    public void configureViewModel() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
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

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        Navigation.findNavController(rootView).navigate(R.id.action_nav_restaurant_details_to_nav_menu_details, bundle);
    }
}
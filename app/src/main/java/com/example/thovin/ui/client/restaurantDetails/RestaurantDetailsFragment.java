package com.example.thovin.ui.client.restaurantDetails;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.UserModel;
import com.example.thovin.viewModels.RestaurantViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RestaurantDetailsFragment extends Fragment implements RecycleViewOnClickListener {

    private View rootView;
    private Context context;

    private RestaurantViewModel restaurantViewModel;
    private UserModel currentRestaurant;

    private TextView name, email, phone;
    private RecyclerView menus;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureViews();

        restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);

        restaurantViewModel.getCurrentRestaurant().observe(getViewLifecycleOwner(), currentRestaurant -> {
            if (currentRestaurant != null) {
                Utility.getSuccessSnackbar(getContext(), view, currentRestaurant.restaurantName, Snackbar.LENGTH_SHORT);
                this.currentRestaurant = currentRestaurant;
                setViews();
            }
        });
    }

    public void configureViews() {
        name = rootView.findViewById(R.id.restaurant_name);
        email = rootView.findViewById(R.id.restaurant_email);
        phone = rootView.findViewById(R.id.restaurant_phone);
        menus = rootView.findViewById(R.id.restaurant_menus);
    }

    public void setViews() {
        name.setText(currentRestaurant.getRestaurantName());
        email.setText(currentRestaurant.getEmail());
        phone.setText(currentRestaurant.getPhone());
        setMenusRecyclerView();
    }


    public void setMenusRecyclerView() {
        menus.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        MenuAdapter menuAdapter = new MenuAdapter(context, currentRestaurant.getMenusId(), this);
        menus.setAdapter(menuAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Navigation.findNavController(rootView).navigate(R.id.action_nav_restaurant_details_to_nav_menu_details);
    }
}
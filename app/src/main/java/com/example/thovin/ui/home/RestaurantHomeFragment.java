package com.example.thovin.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.example.thovin.models.AuthResult;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RestaurantHomeFragment extends Fragment implements RecycleViewOnClickListener {

    View rootView;
    private Context context;

    // --- User view model
    private UserViewModel userViewModel;
    private AuthResult currentUser;

    private TextView name, email, phone;
    private RecyclerView menus;

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
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureViews();
    }

    public void configureViews() {
        name = rootView.findViewById(R.id.restaurant_name);
        email = rootView.findViewById(R.id.restaurant_email);
        phone = rootView.findViewById(R.id.restaurant_phone);
        menus = rootView.findViewById(R.id.restaurant_menus);
    }


//    public void setMenusRecyclerView() {
//        menus.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        MenuAdapter menuAdapter = new MenuAdapter(context, currentRestaurant.getMenusId(), this);
//        menus.setAdapter(menuAdapter);
//    }

    @Override
    public void onItemClick(int position) {
    }
}
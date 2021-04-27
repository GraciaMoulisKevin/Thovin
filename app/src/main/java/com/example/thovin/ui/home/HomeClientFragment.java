package com.example.thovin.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.models.user.UserModel;
import com.example.thovin.ui.auth.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeClientFragment extends Fragment {

    private View rootView;
    private UserViewModel userViewModel;
    private Boolean firstStart = true;

    public HomeClientFragment() {
    }

    public static HomeClientFragment newInstance() {
        return new HomeClientFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home_client, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        if (userViewModel.getCurrentUser().getValue() == null) getActivity().finish();
        else if (firstStart) {
            userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> Utility.getSuccessSnackbar(getContext(), view, getString(R.string.connection_success) + " " + user.getUser().getFullName(), Snackbar.LENGTH_LONG).show());
            firstStart = false;
        }

        configureCategorySpinner();
        configureRecyclerView();

    }

    public void configureRecyclerView() {
        ArrayList<UserModel> restaurants = userViewModel.getRestaurants();

        RecyclerView recyclerView = rootView.findViewById(R.id.restaurant_recycler_view);

        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(getContext(), restaurants);
        recyclerView.setAdapter(restaurantAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }


    private void configureCategorySpinner() {
        // Mock category until API not complete
        List<String> categories = Arrays.asList("Catégorie", "Sushis", "Burger", "Saladerie", "Pizza");

        Spinner categorySpinner = rootView.findViewById(R.id.category_spinner);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String item = parent.getItemAtPosition(position).toString();
                    Snackbar.make(rootView, "Selected: " + item, Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}



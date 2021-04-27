package com.example.thovin.ui.home.client;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.models.auth.AuthResult;
import com.example.thovin.models.user.UserModel;
import com.example.thovin.viewModels.RestaurantViewModel;
import com.example.thovin.viewModels.UserViewModel;
import com.example.thovin.ui.home.RestaurantAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeClientFragment extends Fragment {

    private View rootView;

    private UserViewModel userViewModel;
    private AuthResult currentUser;

    private RestaurantViewModel restaurantViewModel;
    private ArrayList<UserModel> restaurants = new ArrayList<>();

    private TextView noRestaurantAvailable;
    private Boolean firstStart = true;
    private RecyclerView recyclerView;

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

        noRestaurantAvailable = rootView.findViewById(R.id.no_restaurant_available);
        configureRecyclerView();
        configureCategorySpinner();


        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);

        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            // --- Kill the activity, user shouldn't be there
            if (user == null) getActivity().finish();

            // --- Welcome message on first start
            if (firstStart) {
                Utility.getSuccessSnackbar(getContext(), rootView, getString(R.string.connection_success) + " " + user.getUser().getFullName(), Snackbar.LENGTH_LONG).show();
                firstStart = false;
            }

            // --- Save the user and load the restaurants list
            currentUser = user;
            restaurantViewModel.loadRestaurant(currentUser.token);
        });


        // Observe restaurants list
        restaurantViewModel.getRestaurants().observe(getViewLifecycleOwner(), result -> {
            int state = restaurantViewModel.getState();
            if (state == Utility.STATE_SUCCESS) {
                // --- If no restaurant found, display a message
                if (result.size() == 0) {
                    Utility.getWarningSnackbar(getContext(), view, getString(R.string.warn_no_restaurant_available), Snackbar.LENGTH_LONG).show();
                    noRestaurantAvailable.setVisibility(View.VISIBLE);
                } else {
                    noRestaurantAvailable.setVisibility(View.GONE);
                    restaurants = result;
                    setRecyclerViewAdapter();
                }
            } else if (state == Utility.STATE_ERROR) {
                noRestaurantAvailable.setVisibility(View.VISIBLE);
                Utility.getErrorSnackbar(getContext(), view, getString(R.string.err_unauthorized), Snackbar.LENGTH_LONG).show();
            } else {
                noRestaurantAvailable.setVisibility(View.VISIBLE);
                Utility.getErrorSnackbar(getContext(), view, getString(R.string.err_occurred), Snackbar.LENGTH_LONG).show();
            }
        });


    }

    /**
     * Configure the RecyclerView containing the list of the restaurants
     */
    public void configureRecyclerView() {
        recyclerView = rootView.findViewById(R.id.restaurant_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setRecyclerViewAdapter();
    }

    /**
     * Set the RecyclerView Adapter with the list of restaurants
     */
    public void setRecyclerViewAdapter() {
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(getContext(), restaurants);
        recyclerView.setAdapter(restaurantAdapter);
    }

    /**
     * Configure the category spinner
     */
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



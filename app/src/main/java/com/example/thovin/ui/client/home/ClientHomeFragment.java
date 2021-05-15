package com.example.thovin.ui.client.home;

import android.content.Context;
import android.os.Bundle;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.adapters.RestaurantAdapter;
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.AuthResult;
import com.example.thovin.models.UserModel;
import com.example.thovin.viewModels.CartViewModel;
import com.example.thovin.viewModels.RestaurantViewModel;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientHomeFragment extends Fragment implements RecycleViewOnClickListener {

    private View rootView;
    private Context context;

    private UserViewModel userViewModel;
    private CartViewModel cartViewModel;
    private AuthResult currentUser;

    private RestaurantViewModel restaurantViewModel;
    private ArrayList<UserModel> restaurants = new ArrayList<>();

    private TextView noRestaurantAvailable;
    private Boolean firstStart = true;
    private RecyclerView recyclerView;

    public ClientHomeFragment() {
    }

    public static ClientHomeFragment newInstance() {
        return new ClientHomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_client_home, container, false);
        context = getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noRestaurantAvailable = rootView.findViewById(R.id.no_restaurant_available);
        configureViewModels();
        configureRecyclerView();
        configureCategorySpinner();

        currentUser = userViewModel.getCurrentUser().getValue();
        if (currentUser == null) startActivity(Utility.getLogoutIntent(context));
        else {
            if (firstStart) {
                firstStart = false;

                // --- Welcome message
                Utility.getSuccessSnackbar(context, rootView, getString(R.string.success_connection)
                        + " " + currentUser.getUser().getFullName(), Snackbar.LENGTH_SHORT).show();

                // --- Init user cart
                cartViewModel.initCart(currentUser.token);

                // --- Load restaurants
                restaurantViewModel.loadRestaurant(currentUser.token);
            }
        }


        // Observe restaurants list
        restaurantViewModel.getRestaurants().observe(getViewLifecycleOwner(), result -> {
            int state = restaurantViewModel.getState();
            if (state == Utility.STATE_SUCCESS) {
                // --- If no restaurant found, display a message
                if (result.size() == 0) {
                    Utility.getWarningSnackbar(context, view, getString(R.string.warn_no_restaurant_available), Snackbar.LENGTH_LONG).show();
                    noRestaurantAvailable.setVisibility(View.VISIBLE);
                } else {
                    noRestaurantAvailable.setVisibility(View.GONE);
                    restaurants = result;
                    setRecyclerViewAdapter();
                }
            } else if (state == Utility.STATE_ERROR) {
                noRestaurantAvailable.setVisibility(View.VISIBLE);
                Utility.getErrorSnackbar(context, view, getString(R.string.err_unauthorized), Snackbar.LENGTH_LONG).show();
            } else {
                noRestaurantAvailable.setVisibility(View.VISIBLE);
                Utility.getErrorSnackbar(context, view, getString(R.string.err_occurred), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Configure all View Models
     */
    public void configureViewModels() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
    }

    /**
     * Configure the RecyclerView containing the list of the restaurants
     */
    public void configureRecyclerView() {
        recyclerView = rootView.findViewById(R.id.restaurant_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        setRecyclerViewAdapter();
    }

    /**
     * Set the RecyclerView Adapter with the list of restaurants
     */
    public void setRecyclerViewAdapter() {
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(context, restaurants, this);
        recyclerView.setAdapter(restaurantAdapter);
    }

    /**
     * Configure the category spinner
     */
    private void configureCategorySpinner() {
        // Mock category until API not complete
        List<String> categories = Arrays.asList("Catégorie", "Sushis", "Burger", "Saladerie", "Pizza");

        Spinner categorySpinner = rootView.findViewById(R.id.category_spinner);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, categories);
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

    // --- Recycler View onClick methods
    @Override
    public void onItemClick(int position) {
        restaurantViewModel.setCurrentRestaurant(restaurants.get(position));
        Navigation.findNavController(rootView).navigate(R.id.action_nav_home_to_nav_restaurant_details);
    }
}



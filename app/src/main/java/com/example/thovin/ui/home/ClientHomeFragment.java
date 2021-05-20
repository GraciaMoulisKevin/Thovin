package com.example.thovin.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.adapters.RestaurantAdapter;
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.AuthResult;
import com.example.thovin.models.MenuModel;
import com.example.thovin.models.UserModel;
import com.example.thovin.viewModels.CartViewModel;
import com.example.thovin.viewModels.OrderViewModel;
import com.example.thovin.viewModels.RestaurantViewModel;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ClientHomeFragment extends Fragment implements RecycleViewOnClickListener {

    private View rootView;
    private Context context;

    // --- User view model
    private UserViewModel userViewModel;
    private AuthResult currentUser;

    // --- Cart view model
    private CartViewModel cartViewModel;

    // --- Restaurant view model
    private RestaurantViewModel restaurantViewModel;
    private ArrayList<UserModel> restaurants = new ArrayList<>();

    // --- Order view model
    private OrderViewModel orderViewModel;

    private TextInputLayout typeSpinner;

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
        configureTypeDropdownMenu();

        currentUser = userViewModel.getCurrentUser().getValue();
        if (currentUser == null) startActivity(Utility.getLogoutIntent(context));
        else {
            if (firstStart) {
                firstStart = false;

                // --- Welcome message
                Utility.getSuccessSnackbar(context, rootView, getString(R.string.success_connection)
                        + " " + currentUser.getUser().getFullName(), Snackbar.LENGTH_SHORT).show();

                // --- Load restaurants
                restaurantViewModel.loadRestaurant(currentUser.token,null);

                // --- Init user cart
                cartViewModel.initCart(currentUser.token);

                // --- Load orders
                orderViewModel.getOrders(currentUser.token);
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
                    recyclerView.setVisibility(View.GONE);
                } else {
                    noRestaurantAvailable.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    restaurants = result;
                    setRecyclerViewAdapter(restaurants);
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
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);

        restaurantViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
                Utility.toggleSpinner(getActivity(), isLoading));
    }

    /**
     * Configure the RecyclerView containing the list of the restaurants
     */
    public void configureRecyclerView() {
        recyclerView = rootView.findViewById(R.id.restaurant_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        setRecyclerViewAdapter(restaurants);
    }

    /**
     * Set the RecyclerView Adapter with the list of restaurants
     */
    public void setRecyclerViewAdapter(ArrayList<UserModel> restaurants) {
        if (restaurants != null && restaurants.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            RestaurantAdapter restaurantAdapter = new RestaurantAdapter(context, restaurants, this);
            recyclerView.setAdapter(restaurantAdapter);
        } else recyclerView.setVisibility(View.GONE);
    }

    // --- Recycler View onClick methods
    @Override
    public void onItemClick(int position, String tag) {
        restaurantViewModel.setCurrentRestaurantMenus(null);
        restaurantViewModel.setCurrentRestaurantProducts(null);
        restaurantViewModel.setCurrentRestaurant(restaurants.get(position));
        Navigation.findNavController(rootView).navigate(R.id.action_nav_home_to_nav_restaurant_details);
    }

    /**
     * Configure the type spinner (Material spinner)
     */
    private void configureTypeDropdownMenu() {
        List<String> types = Utility.PRODUCT_TYPE_TRANSLATE;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.material_dropdown_items, types);

        TextInputLayout typeSpinner = rootView.findViewById(R.id.type_spinner);
        AutoCompleteTextView spinner = (AutoCompleteTextView) typeSpinner.getEditText();
        spinner.setAdapter(adapter);

        spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String type = Utility.getProductType(s.toString());
                Snackbar.make(rootView, type, Snackbar.LENGTH_SHORT).show();
                update(type);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void update(String type) {
        restaurantViewModel.loadRestaurant(currentUser.token,type);
    }
}
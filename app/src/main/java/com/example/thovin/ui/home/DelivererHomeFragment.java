package com.example.thovin.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.adapters.OrderAdapter;
import com.example.thovin.adapters.RestaurantAdapter;
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.AuthResult;
import com.example.thovin.models.OrderResult;
import com.example.thovin.models.UserModel;
import com.example.thovin.viewModels.CartViewModel;
import com.example.thovin.viewModels.OrderViewModel;
import com.example.thovin.viewModels.RestaurantViewModel;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DelivererHomeFragment extends Fragment implements RecycleViewOnClickListener {

    private View rootView;
    private Context context;

    // --- User view model
    private UserViewModel userViewModel;
    private AuthResult currentUser;

    // --- Order view model
    private OrderViewModel orderViewModel;

    private TextView noOrdersAvailable;
    private Boolean firstStart = true;
    private RecyclerView recyclerView;

    public DelivererHomeFragment() {
    }

    public static DelivererHomeFragment newInstance() {
        return new DelivererHomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_deliverer_home, container, false);
        context = getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noOrdersAvailable = rootView.findViewById(R.id.no_pending_order);
        configureViewModels();
        configureRecyclerView();

        currentUser = userViewModel.getCurrentUser().getValue();
        if (currentUser == null) startActivity(Utility.getLogoutIntent(context));
        else {
            if (firstStart) {
                firstStart = false;

                // --- Welcome message
                Utility.getSuccessSnackbar(context, rootView, getString(R.string.success_connection)
                        + " " + currentUser.getUser().getFullName(), Snackbar.LENGTH_SHORT).show();

                // --- Load orders
                orderViewModel.getOrders(currentUser.token);
            }
        }

        // Observe orders list
        orderViewModel.getHistoric().observe(getViewLifecycleOwner(), result -> {
            if (result.size() == 0) {
                Utility.getWarningSnackbar(context, view, getString(R.string.warn_no_pending_order), Snackbar.LENGTH_LONG).show();
                noOrdersAvailable.setVisibility(View.VISIBLE);
            } else {
                setRecyclerViewAdapter(result);
            }
        });

        // Timer
        final Handler handler = new Handler();
        final int delay = 10000; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {
                handler.postDelayed(this, delay);
                orderViewModel.getOrders(currentUser.token);
                Utility.getWarningSnackbar(context, view, "Vive Android c fo", Snackbar.LENGTH_LONG).show();
            }
        }, delay);
    }

    /**
     * Configure all View Models
     */
    public void configureViewModels() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        orderViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
                Utility.toggleSpinner(getActivity(), isLoading));
    }

    /**
     * Configure the RecyclerView containing the orders
     */
    public void configureRecyclerView() {
        recyclerView = rootView.findViewById(R.id.order_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    /**
     * Set the RecyclerView Adapter with the list of orders
     */
    public void setRecyclerViewAdapter(ArrayList<OrderResult> orders) {
        OrderAdapter orderAdapter = new OrderAdapter(context, orders, this);
        recyclerView.setAdapter(orderAdapter);
    }

    // --- Recycler View onClick methods
    @Override
    public void onItemClick(int position, String tag) {
    }
}
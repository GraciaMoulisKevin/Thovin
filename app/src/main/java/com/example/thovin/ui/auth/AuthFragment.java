package com.example.thovin.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.google.android.material.snackbar.Snackbar;

public class AuthFragment extends Fragment {

    private View rootView;

    public AuthFragment() {
    }

    public static AuthFragment newInstance() {
        return (new AuthFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_auth, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // --- Navigate to client authentication
        Button navToClientAuth = rootView.findViewById(R.id.nav_to_client_connection);
        navToClientAuth.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_nav_auth_to_nav_auth_client));

        // --- Navigate to deliverer authentication
        Button navToDelivererAuth = rootView.findViewById(R.id.nav_to_deliverer_connection);
        navToDelivererAuth.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_nav_auth_to_nav_auth_deliverer));

        // --- Navigate to restaurant authentication
        Button navToRestaurantAuth = rootView.findViewById(R.id.nav_to_restaurant_connection);
        navToRestaurantAuth.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_nav_auth_to_nav_auth_restaurant));
    }
}

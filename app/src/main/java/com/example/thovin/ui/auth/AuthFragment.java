package com.example.thovin.ui.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.thovin.MainActivity;
import com.example.thovin.R;
import com.example.thovin.Utility;
import com.google.android.material.snackbar.Snackbar;

public class AuthFragment extends Fragment {

    private View rootView;
    private UserViewModel userViewModel;

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
        Button navToClientAuth = rootView.findViewById(R.id.fg_auth_client_btn);
        navToClientAuth.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_nav_auth_to_nav_auth_client));

        // --- Navigate to deliverer authentication
        Button navToDelivererAuth = rootView.findViewById(R.id.fg_auth_deliverer_btn);
        navToDelivererAuth.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_nav_auth_to_nav_auth_deliverer));

        // --- Navigate to restaurant authentication
        rootView.findViewById(R.id.fg_auth_restaurant_btn).setOnClickListener(v -> {
            Snackbar.make(v, "Connection restaurateur!", Snackbar.LENGTH_LONG).show();
        });
    }
}

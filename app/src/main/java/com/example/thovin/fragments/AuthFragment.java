package com.example.thovin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.thovin.MainActivity;
import com.example.thovin.R;
import com.example.thovin.services.AuthServices;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class AuthFragment extends Fragment {

    public AuthFragment() {}

    public static AuthFragment newInstance() {
        return (new AuthFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_auth, container, false);
        
        // --- Handle onclick Client authentication button
        rootView.findViewById(R.id.fg_auth_client_btn).setOnClickListener(v -> {
            ((MainActivity) Objects.requireNonNull(getActivity())).showFragment(MainActivity.FRAGMENT_AUTHENTICATION_CLIENT);
        });

        // --- Handle onclick Deliverer authentication button
        rootView.findViewById(R.id.fg_auth_deliverer_btn).setOnClickListener(v -> {
            ((MainActivity) Objects.requireNonNull(getActivity())).showFragment(MainActivity.FRAGMENT_AUTHENTICATION_DELIVERER);
        });

        // --- Handle onclick Restaurant authentication button
        rootView.findViewById(R.id.fg_auth_restaurant_btn).setOnClickListener(v -> {
            Snackbar.make(v, "Connection restaurateur!", Snackbar.LENGTH_LONG).show();
        });


        return rootView;
    }
}

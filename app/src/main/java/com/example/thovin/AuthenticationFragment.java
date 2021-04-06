package com.example.thovin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class AuthenticationFragment extends Fragment {

    public AuthenticationFragment() {}

    public static AuthenticationFragment newInstance() {
        return (new AuthenticationFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_authentication, container, false);
        
        // --- Handle onclick Client authentication button
        rootView.findViewById(R.id.fragment_authentication_client_btn).setOnClickListener(v -> {
            ((MainActivity) Objects.requireNonNull(getActivity())).showFragment(MainActivity.FRAGMENT_AUTHENTICATION_CLIENT);
        });

        // --- Handle onclick Deliverer authentication button
        rootView.findViewById(R.id.fragment_authentication_deliverer_btn).setOnClickListener(v -> {
            ((MainActivity) Objects.requireNonNull(getActivity())).showFragment(MainActivity.FRAGMENT_AUTHENTICATION_DELIVERER);
        });

        // --- Handle onclick Restaurant authentication button
        rootView.findViewById(R.id.fragment_authentication_restaurant_btn).setOnClickListener(v -> {
            Snackbar.make(v, "Connection restaurateur!", Snackbar.LENGTH_LONG).show();
        });


        return rootView;
    }
}

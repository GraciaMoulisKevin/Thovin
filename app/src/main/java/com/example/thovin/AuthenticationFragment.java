package com.example.thovin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.example.thovin.databinding.ActivityMainBinding;
import com.example.thovin.databinding.FragmentAuthenticationBinding;
import com.example.thovin.databinding.FragmentCartBinding;
import com.google.android.material.snackbar.Snackbar;

public class AuthenticationFragment extends Fragment {

    public AuthenticationFragment() {}

    public static AuthenticationFragment newInstance() {
        return (new AuthenticationFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_authentication, container, false);
        
        // --- Handle onclick Client authentication button
        Button clientBtn = rootView.findViewById(R.id.authentication_client_btn);
        clientBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AuthenticationClientActivity.class);
            startActivity(intent);
        });

        // --- Handle onclick Deliverer authentication button
        Button delivererBtn = rootView.findViewById(R.id.authentication_deliverer_btn);
        delivererBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AuthenticationDelivererActivity.class);
            startActivity(intent);
        });

        // --- Handle onclick Restaurant authentication button
        Button restaurantBtn = rootView.findViewById(R.id.authentication_restaurant_btn);
        restaurantBtn.setOnClickListener(v -> {
            Snackbar.make(v, "Connection restaurateur!", Snackbar.LENGTH_LONG).show();
        });


        return rootView;
    }
}

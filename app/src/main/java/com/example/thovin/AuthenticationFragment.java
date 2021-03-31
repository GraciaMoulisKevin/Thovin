package com.example.thovin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

public class AuthenticationFragment extends Fragment {

    private Button clientBtn;
    private Button delivererBtn;
    private Button restaurantBtn;

    public static AuthenticationFragment newInstance() {
        return (new AuthenticationFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_authentication, container, false);

        // --- Handle onclick Client authentication button
        clientBtn = rootView.findViewById(R.id.authentication_btn_client);
        clientBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AuthenticationClientActivity.class);
            startActivity(intent);
        });

        // --- Handle onclick Deliverer authentication button
        delivererBtn = rootView.findViewById(R.id.authentication_btn_deliverer);
        delivererBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AuthenticationDelivererActivity.class);
            startActivity(intent);
        });

        // --- Handle onclick Restaurant authentication button
        rootView.findViewById(R.id.authentication_btn_restaurant).setOnClickListener(v -> {
            Snackbar.make(v, "Connection restaurateur!", Snackbar.LENGTH_LONG).show();
        });


        return rootView;
    }
}

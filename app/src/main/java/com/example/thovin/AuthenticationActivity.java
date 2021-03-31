package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        // --- Handle onclick Client authentication button
        findViewById(R.id.authentication_btn_client).setOnClickListener(v -> {
            Snackbar snackbar = Snackbar.make(v, "Connection client!", Snackbar.LENGTH_LONG);
            snackbar.show();
        });

        // --- Handle onclick Deliverer authentication button
        findViewById(R.id.authentication_btn_deliverer).setOnClickListener(v -> {
            Snackbar snackbar = Snackbar.make(v, "Connection livreur!", Snackbar.LENGTH_LONG);
            snackbar.show();
        });

        // --- Handle onclick Restaurant authentication button
        findViewById(R.id.authentication_btn_restaurant).setOnClickListener(v -> {
            Snackbar snackbar = Snackbar.make(v, "Connection restaurateur!", Snackbar.LENGTH_LONG);
            snackbar.show();
        });
    }
}
package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        // --- Handle onclick Client authentication button
        Button clientButton = findViewById(R.id.authentication_btn_client);
        clientButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AuthenticationClientActivity.class);
            startActivity(intent);
        });

        // --- Handle onclick Deliverer authentication button
        findViewById(R.id.authentication_btn_deliverer).setOnClickListener(v -> {
            Intent intent = new Intent(this, AuthenticationDeliverer.class);
            startActivity(intent);
        });

        // --- Handle onclick Restaurant authentication button
        findViewById(R.id.authentication_btn_restaurant).setOnClickListener(v -> {
            Snackbar.make(v, "Connection restaurateur!", Snackbar.LENGTH_LONG).show();
        });
    }
}
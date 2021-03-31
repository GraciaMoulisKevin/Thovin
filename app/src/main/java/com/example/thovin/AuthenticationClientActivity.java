package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

public class AuthenticationClientActivity extends AppCompatActivity {

    private Button connectBtn;
    private Button registerBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_client);

        connectBtn = findViewById(R.id.authenticationClient_connection_btn);
        connectBtn.setOnClickListener(v -> {
            Snackbar.make(v, "Vous êtes connecté!", Snackbar.LENGTH_SHORT).show();
        });

        registerBtn = findViewById(R.id.authenticationClient_register_btn);
        registerBtn.setOnClickListener(v -> {
            Snackbar.make(v, "Vous êtes inscrit!", Snackbar.LENGTH_SHORT).show();
        });

        cancelBtn = findViewById(R.id.authenticationClient_cancel_btn);
        cancelBtn.setOnClickListener(v -> {
            Snackbar.make(v, "Annulation !", Snackbar.LENGTH_SHORT).show();
        });
    }
}
package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;

public class CartActivity extends AppCompatActivity {

    private Button cart, profil, cancel, validate;
    private ScrollView articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        articles = findViewById(R.id.cart_articles);

        // Chargement de la scroll view

        // Buttons
        cart = findViewById(R.id.cart_cart_btn);
        profil = findViewById(R.id.cart_profil);
        validate = findViewById(R.id.cart_validate_btn);
        cancel = findViewById(R.id.cart_cancel_btn);

        validate.setText(""+ cartTotalPrice());

        cart.setOnClickListener(v -> finish());

        profil.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfilActivity.class);
            startActivity(intent);
        });
    }

    private double cartTotalPrice(){
        // calcul prix du panier selon "articles"
        return 32.70;
    }
}
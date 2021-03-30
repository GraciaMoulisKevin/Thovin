package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

public class CartActivity extends AppCompatActivity {

    private Button panier, profil, cancel, validate;
    private ScrollView articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        articles = (ScrollView) findViewById(R.id.panier_articles);

        // Chargement de la scroll view

        // Buttons
        panier = (Button) findViewById(R.id.panier_panier);
        profil = (Button) findViewById(R.id.panier_profil);
        cancel = (Button) findViewById(R.id.panier_cancel);
        validate = (Button) findViewById(R.id.panier_validate);

        validate.setText(""+prixPanier());

        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_profil = new Intent(CartActivity.this, ProfilActivity.class);
                startActivity(intent_profil);
            }
        });
    }

    private double prixPanier(){
        // calcul prix du panier selon "articles"
        return 32.70;
    }
}
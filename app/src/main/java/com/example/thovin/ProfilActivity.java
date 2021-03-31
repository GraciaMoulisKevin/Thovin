package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfilActivity extends AppCompatActivity {

    private Button editButton, panier, close;
    private TextView name, firstName, mail, address, additionalAddress, city, postalCode, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        name = findViewById(R.id.profil_nom);
        firstName = findViewById(R.id.profil_prenom);
        mail = findViewById(R.id.profil_email);
        address = findViewById(R.id.profil_adresse);
        additionalAddress = findViewById(R.id.profil_compAdresse);
        city = findViewById(R.id.profil_ville);
        postalCode = findViewById(R.id.profil_codePostal);
        phone = findViewById(R.id.profil_tel);

        loadData();

        editButton = findViewById(R.id.profil_edit_btn);
        panier = findViewById(R.id.profil_cart);
        close = findViewById(R.id.profil_close);

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfilEditorActivity.class);
            startActivity(intent);
        });

        close.setOnClickListener(v -> finish());

        panier.setOnClickListener(v -> {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });

    }

    private void loadData(){
        name.setText("Weebosaurus");
        firstName.setText("Rex");
        mail.setText("toto@letrain.fr");
        address.setText("117 Avenue du jambon perdu");
        additionalAddress.setText("Sans sauce");
        city.setText("Issou");
        postalCode.setText("34000");
        phone.setText("0636303630");
    }

}
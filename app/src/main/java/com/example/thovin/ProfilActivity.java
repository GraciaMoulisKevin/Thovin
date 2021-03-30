package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfilActivity extends AppCompatActivity {

    private Button editButton, panier, close;
    private TextView nom, prenom, email, adresse, compAdresse, ville, codePostal, tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        nom = (TextView) findViewById(R.id.profil_nom);
        prenom = (TextView) findViewById(R.id.profil_prenom);
        email = (TextView) findViewById(R.id.profil_email);
        adresse = (TextView) findViewById(R.id.profil_adresse);
        compAdresse = (TextView) findViewById(R.id.profil_compAdresse);
        ville = (TextView) findViewById(R.id.profil_ville);
        codePostal = (TextView) findViewById(R.id.profil_codePostal);
        tel = (TextView) findViewById(R.id.profil_tel);

        loadData();

        editButton = (Button) findViewById(R.id.profil_editButton);
        panier = (Button) findViewById(R.id.profil_panier);
        close = (Button) findViewById(R.id.profil_close);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_profilEditor = new Intent(ProfilActivity.this, ProfilEditorActivity.class);
                startActivity(intent_profilEditor);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_panier = new Intent(ProfilActivity.this, CartActivity.class);
                startActivity(intent_panier);
            }
        });

    }

    private void loadData(){
        nom.setText("Weebosaurus");
        prenom.setText("Rex");
        email.setText("toto@letrain.fr");
        adresse.setText("117 Avenue du jambon perdu");
        compAdresse.setText("Sans sauce");
        ville.setText("Issou");
        codePostal.setText("34000");
        tel.setText("0636303630");
    }

}
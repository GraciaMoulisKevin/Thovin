package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private ImageView homePicture;
    private Button cart;
    private Button profil;
    private EditText addressInput;
    private Button position;
    private EditText keywords;
    private Spinner categories;
    private ScrollView restaurants;
    private Button connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lien entre classe / layout
        homePicture = findViewById(R.id.mainActivity_home_picture);
        cart = findViewById(R.id.mainActivity_cart);
        profil = findViewById(R.id.mainActivity_profil);
        addressInput = findViewById(R.id.mainActivity_address_input);
        position = findViewById(R.id.mainActivity_position_btn);
        keywords = findViewById(R.id.mainActivity_keyword);
        categories = findViewById(R.id.mainActivity_spinner_categorie);
        restaurants = findViewById(R.id.mainActivity_scroll_restaurant);
        connection = findViewById(R.id.mainActivity_connection);

        // Modif
        position.setEnabled(false);

        // Spinner
        String[] typeRestaurant = {"Sushis","Burger","Saladerie","Pizza"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeRestaurant);
        categories.setAdapter(adapter);

        // Listeners
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addressInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() != 0){
                    position.setEnabled(true);
                }else{
                    position.setEnabled(false);
                }
            }
        });

        position.setOnClickListener(v -> {
            // Refresh de "restaurants" avec champ de "saisieAdresse"
        });

        keywords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Refresh de "restaurants" avec "s"
            }
        });

        // Changement activités
        cart.setOnClickListener(v -> {
            Intent intent_panier = new Intent(this, CartActivity.class);
            startActivity(intent_panier);
        });

        profil.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfilActivity.class);
            startActivity(intent);
        });

        connection.setOnClickListener(v -> {
            Intent intent = new Intent(this, AuthenticationActivity.class);
            startActivity(intent);
        });

    }
}
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
    private Button panier;
    private Button profil;
    private EditText saisieAdresse;
    private Button localisation;
    private EditText keywords;
    private Spinner categories;
    private ScrollView restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bis);

//        // Lien entre classe / layout
//        homePicture = (ImageView) findViewById(R.id.MainActivity_HomePicture);
//        panier = (Button) findViewById(R.id.MainActivity_Panier);
//        profil = (Button) findViewById(R.id.MainActivity_Profil);
//        saisieAdresse = (EditText) findViewById(R.id.MainActivity_saisieAdresse);
//        localisation = (Button) findViewById(R.id.MainActivity_Localisation);
//        keywords = (EditText) findViewById(R.id.MainActivity_keyword);
//        categories = (Spinner) findViewById(R.id.MainActivity_SpinnerCategorie);
//        restaurants = (ScrollView) findViewById(R.id.MainActivity_ScrollRestaurant);
//
//        // Modif
//        localisation.setEnabled(false);
//
//        // Spinner
//        String[] typeRestaurant = {"tmp1","tmp2","tmp3","tmp4"};
//        ArrayAdapter<String> adpater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeRestaurant);
//        categories.setAdapter(adpater);
//
//        // Listeners
//        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        saisieAdresse.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(s.toString().length() != 0){
//                    localisation.setEnabled(true);
//                }else{
//                    localisation.setEnabled(false);
//                }
//            }
//        });
//
//        localisation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Refresh de "restaurants" avec champ de "saisieAdresse"
//            }
//        });
//
//        keywords.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // Refresh de "restaurants" avec "s"
//            }
//        });
//
//        // Changement activités
//        panier.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_panier = new Intent(MainActivity.this, CartActivity.class);
//                startActivity(intent_panier);
//            }
//        });
//        profil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_profil = new Intent(MainActivity.this, ProfilActivity.class);
//                startActivity(intent_profil);
//            }
//        });
    }
}
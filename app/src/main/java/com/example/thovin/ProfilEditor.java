package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfilEditor extends AppCompatActivity {

    private Button oui, non;
    private EditText nom, prenom, email, mdp1, mdp2, adr, compAdr, ville, codePostal, tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_editor);

        nom = (EditText) findViewById(R.id.profilEdit_nom);
        prenom = (EditText) findViewById(R.id.profilEdit_prenom);
        email = (EditText) findViewById(R.id.profilEdit_email);
        mdp1 = (EditText) findViewById(R.id.profilEdit_pass1);
        mdp2 = (EditText) findViewById(R.id.profilEdit_pass2);
        adr = (EditText) findViewById(R.id.profilEdit_adresse);
        compAdr = (EditText) findViewById(R.id.profilEdit_compAdresse);
        ville = (EditText) findViewById(R.id.profilEdit_ville);
        codePostal = (EditText) findViewById(R.id.profilEdit_codePostal);
        tel = (EditText) findViewById(R.id.profilEdit_tel);

        loadData();

        // Button
        oui = (Button) findViewById(R.id.profilEdit_save);
        non = (Button) findViewById(R.id.profilEdit_cancel);

        oui.setEnabled(false);

        oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // rajouter du code pour modifier la dataUser
                finish();
            }
        });
        non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // PassCheck
        mdp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mdp2.getText().toString().equals(s.toString())){
                    oui.setEnabled(true);
                }else{
                    oui.setEnabled(false);
                }
            }
        });
        mdp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mdp1.getText().toString().equals(s.toString())){
                    oui.setEnabled(true);
                }else{
                    oui.setEnabled(false);
                }
            }
        });
    }

    // En attendant d'avoir une base de donnée
    private void loadData(){
        nom.setHint("Weebosaurus");
        prenom.setHint("Rex");
        email.setHint("toto@letrain.fr");
        adr.setHint("117 Avenue du jambon perdu");
        compAdr.setHint("Sans sauce");
        ville.setHint("Issou");
        codePostal.setHint("34000");
        tel.setHint("0636303630");
    }
}
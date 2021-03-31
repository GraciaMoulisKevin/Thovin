package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfilEditorActivity extends AppCompatActivity {

    private Button saveBtn, cancelBtn;
    private EditText name, firstName, mail, password, passwordVerification, address, additionalAddress, city, postalCode, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_editor);

        name = findViewById(R.id.profilEdit_nom);
        firstName = findViewById(R.id.profilEdit_prenom);
        mail = findViewById(R.id.profilEdit_email);
        password = findViewById(R.id.profilEdit_pass1);
        passwordVerification = findViewById(R.id.profilEdit_pass2);
        address = findViewById(R.id.profilEdit_adresse);
        additionalAddress = findViewById(R.id.profilEdit_compAdresse);
        city = findViewById(R.id.profilEdit_ville);
        postalCode = findViewById(R.id.profilEdit_codePostal);
        phone = findViewById(R.id.profilEdit_tel);

        loadData();

        // Button
        saveBtn = findViewById(R.id.profilEdit_save);
        cancelBtn = findViewById(R.id.profilEdit_cancel);

        saveBtn.setEnabled(false);

        saveBtn.setOnClickListener(v -> {
            // rajouter du code pour modifier la dataUser
            finish();
        });

        cancelBtn.setOnClickListener(v -> finish());

        // PassCheck
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(passwordVerification.getText().toString().equals(s.toString())){
                    saveBtn.setEnabled(true);
                }else{
                    saveBtn.setEnabled(false);
                }
            }
        });
        
        passwordVerification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(password.getText().toString().equals(s.toString())){
                    saveBtn.setEnabled(true);
                }else{
                    saveBtn.setEnabled(false);
                }
            }
        });
    }

    // En attendant d'avoir une base de donnée
    private void loadData(){
        name.setHint("Weebosaurus");
        firstName.setHint("Rex");
        mail.setHint("toto@letrain.fr");
        address.setHint("117 Avenue du jambon perdu");
        additionalAddress.setHint("Sans sauce");
        city.setHint("Issou");
        postalCode.setHint("34000");
        phone.setHint("0636303630");
    }
}
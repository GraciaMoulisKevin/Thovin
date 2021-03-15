package com.example.thovin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // --- Quand on exécute l'application sur son mobile personnel, il faut utiliser l'adresse IP du PC
    // Pour trouver l'IP: Window: ipconfig | Mac: ifconfig
    // Sinon, avec une machine virtuelle, on utilise localhost
    private final String BASE_URL = "http://192.168.1.13:8888/v1/";

    private Retrofit retrofit;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- init Retrofit & Our API Services
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(APIService.class);

        // --- init user TextView display
        TextView usersDisplay = findViewById(R.id.txt_users_display);

        // --- set our button to use the API Service
        Button btn = findViewById(R.id.btn_getAllUser);
        btn.setOnClickListener(v -> {

            // --- Try to get all users
            Call<List<UserModel>> users = apiService.getAllUser();

            // --- Handle Callback response
            users.enqueue(new Callback<List<UserModel>>() {
                @Override
                public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

                    if (response.code() == 200) {
                        List<UserModel> users = response.body();

                        for (UserModel user : users) {
                            usersDisplay.append(user.getName() + " " + user.getFirstName() + "\n");
                        }
                    }

                    if (response.code() == 404){
                        Toast.makeText(getApplicationContext(), "No user found", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<UserModel>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
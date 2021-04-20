package com.example.thovin.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.thovin.MainActivity;
import com.example.thovin.POJO.LoginPOJO;
import com.example.thovin.POJO.RegisterPOJO;
import com.example.thovin.R;
import com.example.thovin.models.AddressModel;
import com.example.thovin.models.ErrResponseModel;
import com.example.thovin.models.AuthResponseModel;
import com.example.thovin.services.AuthServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthClientFragment extends Fragment {

    // --- Root view
    private View rootView;

    // --- APIServices
    private AuthServices authServices;

    // --- Inputs
    private TextInputEditText login_email, login_password,
            register_lastName, register_firstName, register_email, register_password, register_check_password, register_street,
            register_additional, register_city, register_country, register_zip, register_phone;

    public AuthClientFragment() {
    }

    public static AuthClientFragment newInstance() {
        return new AuthClientFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_auth_client, container, false);

        // --- Configuration
        configureAPIServices();
        configureTextInputEditText();
        configureButton();

        return rootView;
    }

    private void configureAPIServices() {
        authServices = MainActivity.retrofit.create(AuthServices.class);
    }

    private void configureTextInputEditText() {
        // --- Login inputs
        login_email = rootView.findViewById(R.id.fg_auth_client_login_email);
        login_password = rootView.findViewById(R.id.fg_auth_client_login_password);

        // --- Register inputs
        register_lastName = rootView.findViewById(R.id.fg_auth_client_last_name);
        register_firstName = rootView.findViewById(R.id.fg_auth_client_first_name);
        register_email = rootView.findViewById(R.id.fg_auth_client_email);
        register_password = rootView.findViewById(R.id.fg_auth_client_password);
        register_check_password = rootView.findViewById(R.id.fg_auth_client_check_password);
        register_street = rootView.findViewById(R.id.fg_auth_client_street);
        register_additional = rootView.findViewById(R.id.fg_auth_client_additional);
        register_city = rootView.findViewById(R.id.fg_auth_client_city);
        register_country = rootView.findViewById(R.id.fg_auth_client_country);
        register_zip = rootView.findViewById(R.id.fg_auth_client_zip);
        register_phone = rootView.findViewById(R.id.fg_auth_client_phone);
    }

    private void configureButton() {
        configureLoginButton();
        configureRegisterButton();
    }

    private void configureLoginButton() {
        Button login_btn = rootView.findViewById(R.id.fg_auth_client_login_btn);
        login_btn.setOnClickListener(v -> {

            // TODO: check them
            String email = login_email.getText().toString();
            String password = login_password.getText().toString();
            LoginPOJO body = new LoginPOJO(email, password);

            // --- Try to login
            Call<AuthResponseModel> user = authServices.login(body);
            user.enqueue(new Callback<AuthResponseModel>() {
                @Override
                public void onResponse(Call<AuthResponseModel> call, Response<AuthResponseModel> response) {

                    if (response.isSuccessful()) {
                        AuthResponseModel authResponse = response.body();
                        Snackbar.make(v, "Bonjour " + authResponse.getUser().getFullName(), Snackbar.LENGTH_LONG).show();

                        MainActivity.setUser(authResponse);
                        ((MainActivity) Objects.requireNonNull(getActivity())).showFragment(MainActivity.FRAGMENT_HOME);
                    } else {
                        handleErrorResponse(response, v);
                    }
                }

                @Override
                public void onFailure(Call<AuthResponseModel> call, Throwable t) {
                    // use t.getMessage() for debugging
                    Snackbar.make(v, getActivity().getString(R.string.err_connection), Snackbar.LENGTH_LONG).show();
                }
            });
        });
    }

    private void configureRegisterButton() {
        Button register_btn = rootView.findViewById(R.id.fg_auth_client_register_btn);
        register_btn.setOnClickListener(v -> {
            // TODO: check them
            String password = register_password.getText().toString();
            String check_password = register_check_password.getText().toString();

            // --- Create the request body
            String firstName = register_firstName.getText().toString();
            String lastName = register_lastName.getText().toString();
            String email = register_email.getText().toString();
            String phone = register_phone.getText().toString();
            AddressModel address = new AddressModel();
            address.setStreet(register_street.getText().toString());
            address.setAdditional(register_additional.getText().toString());
            address.setCity(register_city.getText().toString());
            address.setCountry(register_country.getText().toString());
            address.setZip(register_zip.getText().toString());

            RegisterPOJO body = new RegisterPOJO(firstName, lastName, email, password, phone, address);
            // --- Try to login
            Call<AuthResponseModel> user = authServices.register(body);
            user.enqueue(new Callback<AuthResponseModel>() {
                @Override
                public void onResponse(Call<AuthResponseModel> call, Response<AuthResponseModel> response) {

                    if (response.isSuccessful()) {
                        AuthResponseModel authResponse = response.body();
                        Snackbar.make(v, "Bienvenue " + authResponse.getUser().getFullName(), Snackbar.LENGTH_LONG).show();

                        MainActivity.setUser(authResponse);
                        ((MainActivity) Objects.requireNonNull(getActivity())).showFragment(MainActivity.FRAGMENT_HOME);
                    } else {
                        handleErrorResponse(response, v);
                    }
                }

                @Override
                public void onFailure(Call<AuthResponseModel> call, Throwable t) {
                    // use t.getMessage() for debugging
                    Snackbar.make(v, getActivity().getString(R.string.err_connection), Snackbar.LENGTH_LONG).show();
                }
            });
        });
    }

    private void handleErrorResponse(Response<AuthResponseModel> response, View v) {
        try {
            // --- Transform as ErrResponseModel
            Gson gson = new Gson();
            ErrResponseModel err = gson.fromJson(response.errorBody().string(), ErrResponseModel.class);

            // --- Display error on screen
            int statusCode = response.code();
            switch (statusCode) {
                case 400:
                    Snackbar.make(v, getActivity().getString(R.string.err_400) + " " + Arrays.toString(err.getFields()), Snackbar.LENGTH_LONG).show();
                    break;
                case 404:
                    Snackbar.make(v, getActivity().getString(R.string.err_404), Snackbar.LENGTH_LONG).show();
                    break;
                case 409:
                    Snackbar.make(v, getActivity().getString(R.string.err_409), Snackbar.LENGTH_LONG).show();
                    break;
                default:
                    Snackbar.make(v, getActivity().getString(R.string.err_occurred), Snackbar.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
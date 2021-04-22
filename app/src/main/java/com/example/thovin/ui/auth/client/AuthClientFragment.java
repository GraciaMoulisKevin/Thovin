package com.example.thovin.ui.auth.client;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.thovin.MainActivity;
import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.models.AddressModel;
import com.example.thovin.models.ErrResponseModel;
import com.example.thovin.models.AuthResponseModel;
import com.example.thovin.services.AuthServices;
import com.example.thovin.ui.auth.LoginPOJO;
import com.example.thovin.ui.auth.RegisterPOJO;
import com.example.thovin.ui.auth.UserViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthClientFragment extends Fragment {

    // --- Root view
    private View rootView;
    private Context context;

    // --- APIServices
    private AuthServices authServices;

    // --- Fields
    private TextInputLayout login_email;
    private TextInputLayout login_password;

    private TextInputLayout register_lastName;
    private TextInputLayout register_firstName;
    private TextInputLayout register_email;
    private TextInputLayout register_password;
    private TextInputLayout register_check_password;
    private TextInputLayout register_street;
    private TextInputLayout register_additional;
    private TextInputLayout register_city;
    private TextInputLayout register_country;
    private TextInputLayout register_zip;
    private TextInputLayout register_phone;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_auth_client, container, false);
        context = getContext();

        // --- Configuration
        authServices = MainActivity.retrofit.create(AuthServices.class);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureTextInputLayout();
        configureButtons();
    }


    private void configureTextInputLayout() {

        // --- Text fields
        login_email = rootView.findViewById(R.id.fg_auth_client_login_email);
        register_lastName = rootView.findViewById(R.id.fg_auth_client_last_name);
        register_firstName = rootView.findViewById(R.id.fg_auth_client_first_name);
        register_email = rootView.findViewById(R.id.fg_auth_client_email);
        register_street = rootView.findViewById(R.id.fg_auth_client_street);
        register_additional = rootView.findViewById(R.id.fg_auth_client_additional);
        register_city = rootView.findViewById(R.id.fg_auth_client_city);
        register_country = rootView.findViewById(R.id.fg_auth_client_country);
        register_zip = rootView.findViewById(R.id.fg_auth_client_zip);
        register_phone = rootView.findViewById(R.id.fg_auth_client_phone);

        ArrayList<TextInputLayout> textFields = new ArrayList<>(Arrays.asList(
                login_email,
                register_lastName,
                register_firstName,
                register_email,
                register_street,
                register_city,
                register_country,
                register_zip,
                register_phone));

        for (TextInputLayout field : textFields) {
            field.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0)
                        Utility.setErrorOnField(context, field, getString(R.string.err_empty_field));
                    else
                        field.setError(null);
                }
            });
        }


        // --- Password fields
        login_password = rootView.findViewById(R.id.fg_auth_client_login_password);
        register_password = rootView.findViewById(R.id.fg_auth_client_password);
        ArrayList<TextInputLayout> passwordFields = new ArrayList<>(Arrays.asList(
                login_password,
                register_password));

        for (TextInputLayout field : passwordFields) {
            field.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0)
                        Utility.setErrorOnField(context, field, getString(R.string.err_empty_field));
                    else if (s.length() < 8)
                        Utility.setErrorOnField(context, field, getString(R.string.err_password_length));
                    else
                        field.setError(null);
                }
            });
        }


        // --- Check Password field
        register_check_password = rootView.findViewById(R.id.fg_auth_client_check_password);
        register_check_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    Utility.setErrorOnField(context, register_check_password, getString(R.string.err_empty_field));
                else if (!s.toString().equals(register_password.getEditText().getText().toString()))
                    Utility.setErrorOnField(context, register_check_password, getString(R.string.err_password_not_equals));
                else
                    register_check_password.setError(null);
            }
        });
    }

    private void configureButtons() {
        configureLoginButton();
        configureRegisterButton();
    }

    private void configureLoginButton() {
        Button login_btn = rootView.findViewById(R.id.fg_auth_client_login_btn);
        login_btn.setOnClickListener(v -> {

            boolean isOk = checkLoginInputs();
            if (isOk) {

                LoginPOJO body = getLoginPOJO();

                // --- Try to login
                Call<AuthResponseModel> authResponse = authServices.login(body);
                authResponse.enqueue(new Callback<AuthResponseModel>() {

                    @Override
                    public void onResponse(Call<AuthResponseModel> call, Response<AuthResponseModel> response) {

                        if (response.isSuccessful()) {
                            AuthResponseModel authResponse = response.body();
                            Utility.getSuccSnackbar(context, v, "Bonjour " + authResponse.getUser().getFullName(), Snackbar.LENGTH_LONG).show();

                            MainActivity.user = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
                            MainActivity.user.setUser(authResponse);
                            //((MainActivity) Objects.requireNonNull(getActivity())).showFragment(MainActivity.FRAGMENT_HOME);
                        } else handleLoginErrResponse(response, v);
                    }

                    @Override
                    public void onFailure(Call<AuthResponseModel> call, Throwable t) {
                        Utility.getWarnSnackbar(context, v, getActivity().getString(R.string.err_connection), Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void configureRegisterButton() {
        Button register_btn = rootView.findViewById(R.id.fg_auth_client_register_btn);
        register_btn.setOnClickListener(v -> {
            boolean isOk = checkRegisterInputs();
            if (isOk) {

                RegisterPOJO body = getRegisterPOJO();

                // --- Try to register
                Call<AuthResponseModel> user = authServices.register(body);
                user.enqueue(new Callback<AuthResponseModel>() {
                    @Override
                    public void onResponse(Call<AuthResponseModel> call, Response<AuthResponseModel> response) {

                        if (response.isSuccessful()) {
                            AuthResponseModel authResponse = response.body();
                            Utility.getSuccSnackbar(context, v, "Bienvenue " + authResponse.getUser().getFullName(), Snackbar.LENGTH_LONG).show();

                            MainActivity.user = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
                            MainActivity.user.setUser(authResponse);
                        } else {
                            handleRegisterErrResponse(response, v);
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponseModel> call, Throwable t) {
                        Utility.getWarnSnackbar(context, v, getActivity().getString(R.string.err_connection), Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


    private void handleLoginErrResponse(Response<AuthResponseModel> response, View v) {
        try {
            switch (response.code()) {
                case 400:
                    Gson gson = new Gson();
                    ErrResponseModel err = gson.fromJson(response.errorBody().string(), ErrResponseModel.class);
                    Utility.getErrSnackbar(context, v, getActivity().getString(R.string.err_400), Snackbar.LENGTH_LONG).show();
                    break;
                case 404:
                    Utility.getErrSnackbar(context, v, getActivity().getString(R.string.err_404), Snackbar.LENGTH_LONG).show();
                    break;
                default:
                    Utility.getErrSnackbar(context, v, getActivity().getString(R.string.err_occurred), Snackbar.LENGTH_LONG).show();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleRegisterErrResponse(Response<AuthResponseModel> response, View v) {
        try {
            switch (response.code()) {
                case 400:
                    Gson gson = new Gson();
                    ErrResponseModel err = gson.fromJson(response.errorBody().string(), ErrResponseModel.class);

                    HashMap<String, TextInputLayout> fields = new HashMap<>();
                    fields.put("register_email", register_email);
                    fields.put("register_zip", register_zip);
                    fields.put("register_phone", register_phone);
                    Utility.setErrorOnFields(context, fields, err.getFields(), getString(R.string.err_400), 1);

                    break;
                case 409:
                    Utility.setErrorOnField(context, register_email, getString(R.string.err_409));
                    break;
                default:
                    Utility.getErrSnackbar(context, v, getActivity().getString(R.string.err_occurred), Snackbar.LENGTH_LONG).show();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean checkLoginInputs() {
        ArrayList<TextInputLayout> fields = new ArrayList<>(Arrays.asList(
                login_email,
                login_password));

        return checkFields(fields);
    }

    private boolean checkRegisterInputs() {
        ArrayList<TextInputLayout> fields = new ArrayList<>(Arrays.asList(
                register_lastName,
                register_firstName,
                register_email,
                register_password,
                register_check_password,
                register_street,
                register_city,
                register_country,
                register_zip,
                register_phone));

        return checkFields(fields);
    }

    private boolean checkFields(ArrayList<TextInputLayout> fields) {
        boolean isOk = true;

        for (TextInputLayout field : fields) {

            // --- Check empty fields
            if (TextUtils.isEmpty(field.getEditText().getText())) {
                Utility.setErrorOnField(context, field, getString(R.string.err_empty_field));
                isOk = false;
            }

            // --- Already erroneous
            if (!TextUtils.isEmpty(field.getError())) {
                isOk = false;
            }
        }

        return isOk;
    }


    private LoginPOJO getLoginPOJO() {
        String email = login_email.getEditText().getText().toString();
        String password = login_password.getEditText().getText().toString();
        return new LoginPOJO(email, password);
    }

    private RegisterPOJO getRegisterPOJO() {
        String firstName = register_firstName.getEditText().getText().toString();
        String lastName = register_lastName.getEditText().getText().toString();
        String email = register_email.getEditText().getText().toString();
        String password = register_password.getEditText().getText().toString();
        String phone = register_phone.getEditText().getText().toString();

        AddressModel address = new AddressModel();
        address.setStreet(register_street.getEditText().getText().toString());
        address.setAdditional(register_additional.getEditText().getText().toString());
        address.setCity(register_city.getEditText().getText().toString());
        address.setCountry(register_country.getEditText().getText().toString());
        address.setZip(register_zip.getEditText().getText().toString());

        return new RegisterPOJO(firstName, lastName, email, password, phone, address);
    }
}
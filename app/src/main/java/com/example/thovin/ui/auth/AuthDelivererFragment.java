package com.example.thovin.ui.auth;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.models.AddressModel;
import com.example.thovin.results.AuthResult;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AuthDelivererFragment extends Fragment {

    public static String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";

    // --- Root view
    private Context context;
    private View rootView;
    private UserViewModel userViewModel;
    private SavedStateHandle savedStateHandle;

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

    public AuthDelivererFragment() { }

    public static AuthDelivererFragment newInstance() {
        return new AuthDelivererFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_auth_deliverer, container, false);

        context = getContext();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RelativeLayout progressSpinner = getActivity().findViewById(R.id.progress_spinner);
        savedStateHandle = Navigation.findNavController(view)
                .getPreviousBackStackEntry()
                .getSavedStateHandle();
        savedStateHandle.set(LOGIN_SUCCESSFUL, false);


        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.logout();

        // --- Loader
        userViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) progressSpinner.setVisibility(View.VISIBLE);
            else progressSpinner.setVisibility(View.INVISIBLE);
        });

        // --- User
        userViewModel.getUser().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result.success) {
                    savedStateHandle.set(LOGIN_SUCCESSFUL, true);
                    Navigation.findNavController(rootView).navigate(R.id.nav_home);
                } else if (result.type == 0){
                    handleLoginError(result);
                } else {
                    handleRegisterError(result);
                }
            }
        });


        configureTextInputLayout();
        configureButtons();
    }

    /**
     * Get a login POJO by looking at each text input layout needed
     * @return the login POJO
     */
    private LoginPOJO getLoginPOJO() {
        String email = login_email.getEditText().getText().toString();
        String password = login_password.getEditText().getText().toString();
        return new LoginPOJO(email, password);
    }

    /**
     * Get a register POJO by looking at each text input layout needed
     * @return the register POJO
     */
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

        return new RegisterPOJO(RegisterPOJO.TYPE_DELIVERER, firstName, lastName, email, password, phone, address);
    }

    /**
     * Configure all text input layout
     */
    private void configureTextInputLayout() {

        // --- Text fields
        login_email = rootView.findViewById(R.id.fg_auth_deliverer_login_email);
        register_lastName = rootView.findViewById(R.id.fg_auth_deliverer_last_name);
        register_firstName = rootView.findViewById(R.id.fg_auth_deliverer_first_name);
        register_email = rootView.findViewById(R.id.fg_auth_deliverer_email);
        register_street = rootView.findViewById(R.id.fg_auth_deliverer_street);
        register_additional = rootView.findViewById(R.id.fg_auth_deliverer_additional);
        register_city = rootView.findViewById(R.id.fg_auth_deliverer_city);
        register_country = rootView.findViewById(R.id.fg_auth_deliverer_country);
        register_zip = rootView.findViewById(R.id.fg_auth_deliverer_zip);
        register_phone = rootView.findViewById(R.id.fg_auth_deliverer_phone);

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
        login_password = rootView.findViewById(R.id.fg_auth_deliverer_login_password);
        register_password = rootView.findViewById(R.id.fg_auth_deliverer_password);
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
        register_check_password = rootView.findViewById(R.id.fg_auth_deliverer_check_password);
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

    /**
     * Configure all buttons
     */
    private void configureButtons() {
        // --- Login button
        Button login_btn = rootView.findViewById(R.id.fg_auth_deliverer_login_btn);
        login_btn.setOnClickListener(v -> {
            boolean isOk = checkLoginInputs();
            if (isOk) login();
        });

        // --- Register button
        Button register_btn = rootView.findViewById(R.id.fg_auth_deliverer_register_btn);
        register_btn.setOnClickListener(v -> {
            boolean isOk = checkRegisterInputs();
            if (isOk) register();
        });
    }

    /**
     * Check all login inputs
     * @return Return true if no error found, else false
     */
    private boolean checkLoginInputs() {
        ArrayList<TextInputLayout> fields = new ArrayList<>(Arrays.asList(
                login_email,
                login_password));

        return checkFields(fields);
    }

    /**
     * Check all register inputs
     * @return Return true if no error found, else false
     */
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

    /**
     * Check all given fields for error or missing value
     * @param fields The fields to check
     * @return Return true if no error found, else false
     */
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

    /**
     * Login
     */
    private void login() {
        LoginPOJO loginPOJO = getLoginPOJO();
        userViewModel.login(loginPOJO);
    }

    /**
     * Register
     */
    private void register() {
        RegisterPOJO registerPOJO = getRegisterPOJO();
        userViewModel.register(registerPOJO);
    }

    /**
     * Handle error on login
     * @param result The authentication result
     */
    private void handleLoginError(AuthResult result) {
        if (result.resCode == -1)
            Utility.getWarningSnackbar(context, rootView, getString(R.string.err_connection), Snackbar.LENGTH_LONG).show();
        else {
            String message;
            switch (result.resCode) {
                case 400:
                    message = getString(R.string.err_400);

                    HashMap<String, TextInputLayout> fields = new HashMap<>();
                    fields.put("login_email", login_email);

                    Utility.setErrorOnFields(context, fields, result.getFields(), message, Utility.TYPE_LOGIN);
                    break;
                case 404:
                    message = getString(R.string.err_404);
                    break;
                default:
                    message = getString(R.string.err_occurred);
                    break;
            }
            Utility.getErrorSnackbar(context, rootView, message, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Handle error on registration
     * @param result The authentication result
     */
    private void handleRegisterError(AuthResult result) {
        if (result.resCode == -1)
            Utility.getWarningSnackbar(context, rootView, getString(R.string.err_connection), Snackbar.LENGTH_LONG).show();

        else {
            String message;
            switch (result.resCode) {
                case 400:
                    message = getString(R.string.err_400);

                    HashMap<String, TextInputLayout> fields = new HashMap<>();
                    fields.put("register_email", register_email);
                    fields.put("register_phone", register_phone);

                    Utility.setErrorOnFields(context, fields, result.getFields(), message, Utility.TYPE_REGISTER);
                    break;
                case 409:
                    message = getString(R.string.err_409);
                    break;
                default:
                    message = getString(R.string.err_occurred);
                    break;
            }
            Utility.getErrorSnackbar(context, rootView, message, Snackbar.LENGTH_LONG).show();
        }
    }
}
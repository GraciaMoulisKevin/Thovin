package com.example.thovin.ui.auth;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.thovin.ClientActivity;
import com.example.thovin.models.LoginModel;
import com.example.thovin.models.RegisterModel;
import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.models.AddressModel;
import com.example.thovin.models.AuthResult;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ClientAuthFragment extends Fragment {

    // --- Root view
    private Context context;
    private View rootView;
    private UserViewModel userViewModel;

    // --- Fields
    private TextInputLayout loginEmail;
    private TextInputLayout loginPassword;

    private TextInputLayout registerLastName;
    private TextInputLayout registerFirstName;
    private TextInputLayout registerEmail;
    private TextInputLayout registerPassword;
    private TextInputLayout registerCheckPassword;
    private TextInputLayout registerStreet;
    private TextInputLayout registerAdditional;
    private TextInputLayout registerCity;
    private TextInputLayout registerCountry;
    private TextInputLayout registerZip;
    private TextInputLayout registerPhone;

    public ClientAuthFragment() {
    }

    public static ClientAuthFragment newInstance() {
        return new ClientAuthFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_client_auth, container, false);
        context = getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // --- Progress Spinner
        userViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> Utility.toggleSpinner(getActivity(), isLoading));

        // --- User
        userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                if (result.success)
                    startActivity(Utility.getHomeIntent(context, ClientActivity.class));
                else if (result.type == 0) handleLoginError(result);
                else handleRegisterError(result);
            }
        });

        configureTextInputLayout();
        configureButtons();
    }

    /**
     * Get a login POJO by looking at each text input layout needed
     *
     * @return the login POJO
     */
    private LoginModel getLoginPOJO() {
        String email = loginEmail.getEditText().getText().toString();
        String password = loginPassword.getEditText().getText().toString();
        return new LoginModel(email, password);
    }

    /**
     * Get a register POJO by looking at each text input layout needed
     *
     * @return the register POJO
     */
    private RegisterModel getRegisterPOJO() {
        String firstName = registerFirstName.getEditText().getText().toString();
        String lastName = registerLastName.getEditText().getText().toString();
        String email = registerEmail.getEditText().getText().toString();
        String password = registerPassword.getEditText().getText().toString();
        String phone = registerPhone.getEditText().getText().toString();

        AddressModel address = new AddressModel();
        address.setStreet(registerStreet.getEditText().getText().toString());
        address.setAdditional(registerAdditional.getEditText().getText().toString());
        address.setCity(registerCity.getEditText().getText().toString());
        address.setCountry(registerCountry.getEditText().getText().toString());
        address.setZip(registerZip.getEditText().getText().toString());

        return new RegisterModel(firstName, lastName, email, password, phone, address, RegisterModel.TYPE_CLIENT);
    }

    /**
     * Configure all text input layout
     */
    private void configureTextInputLayout() {

        // --- Text fields
        loginEmail = rootView.findViewById(R.id.fg_auth_client_login_email);
        registerLastName = rootView.findViewById(R.id.fg_auth_client_last_name);
        registerFirstName = rootView.findViewById(R.id.fg_auth_client_first_name);
        registerEmail = rootView.findViewById(R.id.fg_auth_client_email);
        registerStreet = rootView.findViewById(R.id.fg_auth_client_street);
        registerAdditional = rootView.findViewById(R.id.fg_auth_client_additional);
        registerCity = rootView.findViewById(R.id.fg_auth_client_city);
        registerCountry = rootView.findViewById(R.id.fg_auth_client_country);
        registerZip = rootView.findViewById(R.id.fg_auth_client_zip);
        registerPhone = rootView.findViewById(R.id.fg_auth_client_phone);

        ArrayList<TextInputLayout> textFields = new ArrayList<>(Arrays.asList(
                loginEmail,
                registerLastName,
                registerFirstName,
                registerEmail,
                registerStreet,
                registerCity,
                registerCountry,
                registerZip,
                registerPhone));

        Utility.addTextChangedListener(context, textFields);

        // --- Password fields
        loginPassword = rootView.findViewById(R.id.fg_auth_client_login_password);
        registerPassword = rootView.findViewById(R.id.fg_auth_client_password);
        registerCheckPassword = rootView.findViewById(R.id.fg_auth_client_check_password);

        Utility.addTextChangedListenerLogin(context, loginPassword);
        Utility.addTextChangedListenerRegister(context, registerPassword, registerCheckPassword);
    }

    /**
     * Configure all buttons
     */
    private void configureButtons() {
        // --- Login button
        Button login_btn = rootView.findViewById(R.id.fg_auth_client_login_btn);
        login_btn.setOnClickListener(v -> {
            boolean isOk = checkLoginInputs();
            if (isOk) login();
        });

        // --- Register button
        Button register_btn = rootView.findViewById(R.id.fg_auth_client_register_btn);
        register_btn.setOnClickListener(v -> {
            boolean isOk = checkRegisterInputs();
            if (isOk) register();
        });
    }

    /**
     * Check all login inputs
     *
     * @return Return true if no error found, else false
     */
    private boolean checkLoginInputs() {
        ArrayList<TextInputLayout> fields = new ArrayList<>(Arrays.asList(
                loginEmail,
                loginPassword));

        return checkFields(fields);
    }

    /**
     * Check all register inputs
     *
     * @return Return true if no error found, else false
     */
    private boolean checkRegisterInputs() {
        ArrayList<TextInputLayout> fields = new ArrayList<>(Arrays.asList(
                registerLastName,
                registerFirstName,
                registerEmail,
                registerPassword,
                registerCheckPassword,
                registerStreet,
                registerCity,
                registerCountry,
                registerZip,
                registerPhone));

        return checkFields(fields);
    }

    /**
     * Check all given fields for error or missing value
     *
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
        LoginModel loginModel = getLoginPOJO();
        userViewModel.login(loginModel);
    }

    /**
     * Register
     */
    private void register() {
        RegisterModel registerModel = getRegisterPOJO();
        userViewModel.register(registerModel);
    }

    /**
     * Handle error on login
     *
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
                    fields.put("login_email", loginEmail);

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
     *
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
                    fields.put("register_email", registerEmail);
                    fields.put("register_zip", registerZip);
                    fields.put("register_phone", registerPhone);

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
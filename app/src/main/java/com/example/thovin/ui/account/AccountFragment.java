package com.example.thovin.ui.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.thovin.R;
import com.example.thovin.models.user.AddressModel;
import com.example.thovin.models.user.UserModel;
import com.example.thovin.ui.auth.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class AccountFragment extends Fragment {

    private View rootView;
    private UserViewModel userViewModel;

    private Button navToAccountEditor;
    private TextInputLayout lastName, firstName, email, street, additional, city, zip, country, phone;
    ArrayList<TextInputLayout> fields = new ArrayList<>();

    private Boolean onEdition = false;


    public AccountFragment() {
    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        configureTextInputLayout();

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        if (userViewModel.getCurrentUser().getValue() == null)
            Navigation.findNavController(view).navigate(R.id.nav_home);

        else userViewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user == null) {
                // TODO: popBackStack in case of
            } else loadData(user.getUser());
        });

        navToAccountEditor = rootView.findViewById(R.id.fg_accounter_edition_btn);
        navToAccountEditor.setOnClickListener(v -> { toggleEditionMode(); });
    }

    private void configureTextInputLayout() {
        lastName = rootView.findViewById(R.id.fg_account_last_name);
        firstName = rootView.findViewById(R.id.fg_account_first_name);
        email = rootView.findViewById(R.id.fg_account_email);
        street = rootView.findViewById(R.id.fg_account_street);
        additional = rootView.findViewById(R.id.fg_account_additional);
        city = rootView.findViewById(R.id.fg_account_city);
        zip = rootView.findViewById(R.id.fg_account_zip);
        country = rootView.findViewById(R.id.fg_account_country);
        phone = rootView.findViewById(R.id.fg_account_phone);

        fields = new ArrayList<>(Arrays.asList(
                lastName,
                firstName,
                email,
                street,
                additional,
                city,
                zip,
                country,
                phone));
    }


    private void loadData(UserModel user) {
        lastName.getEditText().setText(user.getLastName());
        firstName.getEditText().setText(user.getFirstName());
        email.getEditText().setText(user.getEmail());
        phone.getEditText().setText(user.getPhone());

        AddressModel userAddress = user.getAddress();
        street.getEditText().setText(userAddress.getStreet());
        additional.getEditText().setText(userAddress.getAdditional());
        city.getEditText().setText(userAddress.getCity());
        zip.getEditText().setText(userAddress.getZip());
        country.getEditText().setText(userAddress.getCountry());
    }

    private void toggleEditionMode() {
        onEdition = !onEdition;

        if (onEdition) {
            for (TextInputLayout field : fields) {
                field.setEnabled(true);
                field.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
            }
        } else {
            for (TextInputLayout field : fields) {
                field.setEnabled(false);
                field.setEndIconMode(TextInputLayout.END_ICON_NONE);
            }
        }
    }
}
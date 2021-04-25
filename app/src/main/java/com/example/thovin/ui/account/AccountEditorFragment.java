package com.example.thovin.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thovin.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class AccountEditorFragment extends Fragment {

    private View rootView;
    private TextInputLayout lastName, firstName, email, street, additional, city, zip, phone;

    public AccountEditorFragment() { }

    public static AccountEditorFragment newInstance() {
        AccountEditorFragment fragment = new AccountEditorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        this.configureTextInputLayout();

        return rootView;
    }

    private void configureTextInputLayout() {
        lastName = this.rootView.findViewById(R.id.fg_account_last_name);
        firstName = this.rootView.findViewById(R.id.fg_account_first_name);
        email = this.rootView.findViewById(R.id.fg_account_email);
        street = this.rootView.findViewById(R.id.fg_account_street);
        additional = this.rootView.findViewById(R.id.fg_account_additional);
        city = this.rootView.findViewById(R.id.fg_account_city);
        zip = this.rootView.findViewById(R.id.fg_account_zip);
        phone = this.rootView.findViewById(R.id.fg_account_phone);

        ArrayList<TextInputLayout> fields = new ArrayList<>(Arrays.asList(
                lastName,
                firstName,
                email,
                street,
                additional,
                city,
                zip,
                phone));

        for (TextInputLayout field : fields) {
            field.setEnabled(true);
        }
    }
}
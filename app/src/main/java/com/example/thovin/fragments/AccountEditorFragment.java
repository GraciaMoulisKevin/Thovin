package com.example.thovin.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thovin.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class AccountEditorFragment extends Fragment {

    private View rootView;
    private TextInputLayout name, firstName, mail, address, additionalAddress, city, postalCode, phone;
    private static final String ARG_USER_DATA = "jsonUserData";

    private JSONObject jsonUserData;

    public AccountEditorFragment() { }

    public static AccountEditorFragment newInstance(JSONObject jsonUserData) {
        AccountEditorFragment fragment = new AccountEditorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_DATA, jsonUserData.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                this.jsonUserData = new JSONObject(getArguments().getString(ARG_USER_DATA));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account_editor, container, false);

        this.configureTextInputLayout();

        if (jsonUserData != null) {
            try {
                Objects.requireNonNull(name.getEditText()).setText(jsonUserData.get("name").toString());
                Objects.requireNonNull(firstName.getEditText()).setText(jsonUserData.get("first_name").toString());
                Objects.requireNonNull(mail.getEditText()).setText(jsonUserData.get("mail").toString());
                Objects.requireNonNull(address.getEditText()).setText(jsonUserData.get("address").toString());
                Objects.requireNonNull(additionalAddress.getEditText()).setText(jsonUserData.get("additional_address").toString());
                Objects.requireNonNull(city.getEditText()).setText(jsonUserData.get("city").toString());
                Objects.requireNonNull(postalCode.getEditText()).setText(jsonUserData.get("postal_code").toString());
                Objects.requireNonNull(phone.getEditText()).setText(jsonUserData.get("phone").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return rootView;
    }

    private void configureTextInputLayout() {
        name = this.rootView.findViewById(R.id.fg_account_editor_name);
        firstName = this.rootView.findViewById(R.id.fg_account_editor_first_name);
        mail = this.rootView.findViewById(R.id.fg_account_editor_email);
        address = this.rootView.findViewById(R.id.fg_account_editor_address);
        additionalAddress = this.rootView.findViewById(R.id.fg_account_editor_additional_address);
        city = this.rootView.findViewById(R.id.fg_account_editor_city);
        postalCode = this.rootView.findViewById(R.id.fg_account_editor_postal_code);
        phone = this.rootView.findViewById(R.id.fg_account_editor_phone);
    }
}
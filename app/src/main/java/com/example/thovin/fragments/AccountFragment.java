package com.example.thovin.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thovin.MainActivity;
import com.example.thovin.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class AccountFragment extends Fragment {

    private View rootView;
    private TextInputLayout name, firstName, mail, address, additionalAddress, city, postalCode, phone;
    private JSONObject jsonUserData;

    public AccountFragment() { }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_account, container, false);

        this.configureTextInputLayout();

        try {
            this.loadData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.rootView.findViewById(R.id.fg_account_edit_btn).setOnClickListener(v -> {
            ((MainActivity) Objects.requireNonNull(getActivity())).showFragment(MainActivity.FRAGMENT_ACCOUNT_EDITOR, jsonUserData);
        });

        return this.rootView;
    }

    private void configureTextInputLayout() {
        name = this.rootView.findViewById(R.id.fg_account_name);
        firstName = this.rootView.findViewById(R.id.fg_account_first_name);
        mail = this.rootView.findViewById(R.id.fg_account_email);
        address = this.rootView.findViewById(R.id.fg_account_address);
        additionalAddress = this.rootView.findViewById(R.id.fg_account_additional_address);
        city = this.rootView.findViewById(R.id.fg_account_city);
        postalCode = this.rootView.findViewById(R.id.fg_account_postal_code);
        phone = this.rootView.findViewById(R.id.fg_account_phone);
    }

    private void loadData() throws JSONException {

        // Mock json object received from the API
        jsonUserData = new JSONObject();
        jsonUserData.put("name", "Weebosaurus");
        jsonUserData.put("first_name", "Rex");
        jsonUserData.put("mail", "toto@letrain.fr");
        jsonUserData.put("address", "117 Avenue du jambon perdu");
        jsonUserData.put("additional_address", "Sans sauce");
        jsonUserData.put("city", "Issou");
        jsonUserData.put("postal_code", "34000");
        jsonUserData.put("phone", "0636303630");

        this.name.getEditText().setText("Weebosaurus");
        this.firstName.getEditText().setText("Rex");
        this.mail.getEditText().setText("toto@letrain.fr");
        this.address.getEditText().setText("117 Avenue du jambon perdu");
        this.additionalAddress.getEditText().setText("Sans sauce");
        this.city.getEditText().setText("Issou");
        this.postalCode.getEditText().setText("34000");
        this.phone.getEditText().setText("0636303630");
    }
}
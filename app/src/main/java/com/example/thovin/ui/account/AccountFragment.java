package com.example.thovin.ui.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thovin.MainActivity;
import com.example.thovin.R;
import com.example.thovin.ui.auth.UserViewModel;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class AccountFragment extends Fragment {

    private View rootView;
    private UserViewModel user;

    private TextInputLayout lastName, firstName, email, street, additional, city, zip, phone;
    private JSONObject jsonUserData;

    public AccountFragment() { }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_account, container, false);

        user = MainActivity.user;

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        configureTextInputLayout();

//        if (user == null) test_user.setText("disconnected");
//        else user.getUser().observe(getViewLifecycleOwner(), val -> {
//            lastName.getEditText().setText(val.getUser().getLastName());
//        });
    }

    private void configureTextInputLayout() {
        lastName = rootView.findViewById(R.id.fg_account_last_name);
        firstName = rootView.findViewById(R.id.fg_account_first_name);
        email = rootView.findViewById(R.id.fg_account_email);
        street = rootView.findViewById(R.id.fg_account_street);
        additional = rootView.findViewById(R.id.fg_account_additional);
        city = rootView.findViewById(R.id.fg_account_city);
        zip = rootView.findViewById(R.id.fg_account_zip);
        phone = rootView.findViewById(R.id.fg_account_phone);
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

        lastName.getEditText().setText("Weebosaurus");
        firstName.getEditText().setText("Rex");
        email.getEditText().setText("toto@letrain.fr");
        street.getEditText().setText("117 Avenue du jambon perdu");
        additional.getEditText().setText("Sans sauce");
        city.getEditText().setText("Issou");
        zip.getEditText().setText("34000");
        phone.getEditText().setText("0636303630");
    }
}
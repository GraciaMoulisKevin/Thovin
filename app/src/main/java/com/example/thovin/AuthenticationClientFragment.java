package com.example.thovin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AuthenticationClientFragment extends Fragment {

    public AuthenticationClientFragment() {}


    public static AuthenticationClientFragment newInstance() {
        return new AuthenticationClientFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_authentication_client, container, false);
    }
}
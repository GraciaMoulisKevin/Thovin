package com.example.thovin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AuthenticationDelivererFragment extends Fragment {


    public AuthenticationDelivererFragment() { }

    public static AuthenticationDelivererFragment newInstance() {
        return new AuthenticationDelivererFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_authentication_deliverer, container, false);
    }
}
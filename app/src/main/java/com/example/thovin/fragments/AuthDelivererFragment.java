package com.example.thovin.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thovin.R;

public class AuthDelivererFragment extends Fragment {


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
        View rootView = inflater.inflate(R.layout.fragment_auth_deliverer, container, false);


        return rootView;
    }
}
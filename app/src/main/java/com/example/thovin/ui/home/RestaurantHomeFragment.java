package com.example.thovin.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thovin.R;

public class RestaurantHomeFragment extends Fragment {

    View rootView;

    public RestaurantHomeFragment() { }

    public static RestaurantHomeFragment newInstance() {
        return new RestaurantHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_restaurant_home, container, false);
        return rootView;
    }
}
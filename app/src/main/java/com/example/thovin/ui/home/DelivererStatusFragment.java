package com.example.thovin.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.thovin.R;

public class DelivererStatusFragment extends Fragment {

    private View rootView;
    private LinearLayout available, unavailable;

    public DelivererStatusFragment() {}

    public static DelivererStatusFragment newInstance(String param1, String param2) { return new DelivererStatusFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_deliverer_status, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        available = rootView.findViewById(R.id.fg_deliverer_status_available);
        unavailable = rootView.findViewById(R.id.fg_deliverer_status_unavailable);

        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        unavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
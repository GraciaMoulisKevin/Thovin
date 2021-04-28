package com.example.thovin.ui.parameters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.card.MaterialCardView;


public class ClientParametersFragment extends Fragment {

    View rootView;
    MaterialCardView logoutButton;
    UserViewModel userViewModel;

    public ClientParametersFragment() { }

    public static ClientParametersFragment newInstance() {
        return new ClientParametersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_client_parameters, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        logoutButton = rootView.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {
            userViewModel.logout();
            startActivity(Utility.getLogoutIntent(getContext()));
        });

    }
}
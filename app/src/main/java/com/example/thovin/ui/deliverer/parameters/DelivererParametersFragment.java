package com.example.thovin.ui.deliverer.parameters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.card.MaterialCardView;

public class DelivererParametersFragment extends Fragment {

    public View rootView;
    private UserViewModel userViewModel;

    public DelivererParametersFragment() {
    }

    public static DelivererParametersFragment newInstance() {
        return new DelivererParametersFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_deliverer_parameters, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);


        MaterialCardView navToStatus = rootView.findViewById(R.id.param_status_card_view);
        navToStatus.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_nav_parameters_to_nav_deliverer_status));

        MaterialCardView logoutButton = rootView.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> {
            userViewModel.logout();
            startActivity(Utility.getLogoutIntent(getContext()));
        });

    }
}
package com.example.thovin.ui.deliverer.parameters;

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
import com.google.android.material.snackbar.Snackbar;

public class DelivererStatusFragment extends Fragment {

    private View rootView;
    private MaterialCardView available, unavailable;
    private UserViewModel userViewModel;

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

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        available = rootView.findViewById(R.id.deliverer_status_available_card_view);
        unavailable = rootView.findViewById(R.id.deliverer_status_unavailable_card_view);

        available.setOnClickListener(v -> {
            Utility.getSuccessSnackbar(getContext(), v, "You're now available!", Snackbar.LENGTH_SHORT).show();
        });

        unavailable.setOnClickListener(v -> {
            Utility.getErrorSnackbar(getContext(), v, "You're now unavailable!", Snackbar.LENGTH_SHORT).show();
        });
    }
}
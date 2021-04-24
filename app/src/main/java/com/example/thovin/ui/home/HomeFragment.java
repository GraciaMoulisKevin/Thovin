package com.example.thovin.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.ui.auth.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private View rootView;
    private UserViewModel userViewModel;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureCategorySpinner();

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        if (userViewModel.getUser().getValue() == null)
            Utility.getWarningSnackbar(getContext(), view, "You're disconnected", Snackbar.LENGTH_LONG).show();
        else
            userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
                Utility.getSuccessSnackbar(getContext(), view, "Welcome " + user.getUser().getFullName(), Snackbar.LENGTH_LONG).show();
        });

    }

    // --- Spinner Adapter methods
    private void configureCategorySpinner() {
        // Mock category until API not complete
        List<String> categories = Arrays.asList("Catégorie", "Sushis", "Burger", "Saladerie", "Pizza");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner categorySpinner = rootView.findViewById(R.id.fg_home_category_spinner);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    // On selecting a spinner item
                    String item = parent.getItemAtPosition(position).toString();

                    // Showing selected spinner item
                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}



package com.example.thovin.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.thovin.MainActivity;
import com.example.thovin.R;
import com.example.thovin.ui.auth.UserViewModel;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private View rootView;
    private UserViewModel user;

    public HomeFragment() {}

    public static HomeFragment newInstance() {
        return (new HomeFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        configureCategorySpinner();
        user = MainActivity.user;

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        TextView test_user = rootView.findViewById(R.id.test_user);

        if (user == null) test_user.setText("disconnected");
        else user.getUser().observe(getViewLifecycleOwner(), val -> {
            test_user.setText(val.getUser().getFullName());
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



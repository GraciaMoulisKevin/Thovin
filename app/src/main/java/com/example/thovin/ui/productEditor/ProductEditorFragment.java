package com.example.thovin.ui.productEditor;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.models.AuthResult;
import com.example.thovin.models.ProductModel;
import com.example.thovin.models.UserModel;
import com.example.thovin.viewModels.RestaurantViewModel;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class ProductEditorFragment extends Fragment {

    private View rootView;
    private Context context;

    // --- User
    private UserViewModel userViewModel;
    private AuthResult currentUser;

    // --- Restaurant
    private RestaurantViewModel restaurantViewModel;
    private UserModel currentRestaurant;

    private TextInputLayout name, description, type;
    private Button addProductBtn, cancelButton;


    public ProductEditorFragment() {
    }

    public static ProductEditorFragment newInstance() {
        return new ProductEditorFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_product_editor, container, false);
        context = getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureViews();
        configureViewModels();

        currentUser = userViewModel.getCurrentUser().getValue();

        addProductBtn.setOnClickListener(v -> {
            restaurantViewModel.addNewProduct(currentUser.token, currentUser.user.id, getNewProduct());
        });

        restaurantViewModel.getCurrentRestaurantProducts().observe(getViewLifecycleOwner(), products -> {
            if (restaurantViewModel.getState() == Utility.STATE_SUCCESS) {
                restaurantViewModel.setState(Utility.STATE_UNDEFINED);
                Utility.getSuccessSnackbar(context, rootView, getString(R.string.success_add_product), Snackbar.LENGTH_SHORT).show();
                Navigation.findNavController(rootView).popBackStack();
            } else if (restaurantViewModel.getState() != Utility.STATE_UNDEFINED)
                Utility.getErrorSnackbar(context, rootView, getString(R.string.error_add_product), Snackbar.LENGTH_SHORT).show();
        });
    }

    /**
     * Configure simple Views
     */
    public void configureViews() {
        name = rootView.findViewById(R.id.product_name);
        description = rootView.findViewById(R.id.product_description);
        type = rootView.findViewById(R.id.product_type);
        configureTypeDropdownMenu();

        addProductBtn = rootView.findViewById(R.id.add_btn);
    }


    /**
     * Configure all View Models
     */
    public void configureViewModels() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);

        // --- Loading spinner
        restaurantViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
                Utility.toggleSpinner(getActivity(), isLoading));
    }

    /**
     * Configure the category spinner
     */
    private void configureTypeDropdownMenu() {
        List<String> types = Utility.PRODUCT_TYPE_TRANSLATE;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.material_dropdown_items, types);

        AutoCompleteTextView spinner = (AutoCompleteTextView) type.getEditText();
        spinner.setAdapter(adapter);
    }


    public ProductModel getNewProduct() {
        String pName = name.getEditText().getText().toString();
        String pDescription = description.getEditText().getText().toString();
        String pType = Utility.getProductType(type.getEditText().getText().toString());

        return new ProductModel(pName, pDescription, pType);
    }


}
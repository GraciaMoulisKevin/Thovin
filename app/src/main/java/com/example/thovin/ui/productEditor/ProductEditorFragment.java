package com.example.thovin.ui.productEditor;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.models.AuthResult;
import com.example.thovin.models.ProductModel;
import com.example.thovin.models.UserModel;
import com.example.thovin.viewModels.RestaurantViewModel;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductEditorFragment extends Fragment {

    private View rootView;
    private Context context;

    // --- User
    private UserViewModel userViewModel;
    private AuthResult currentUser;

    // --- Restaurant
    private RestaurantViewModel restaurantViewModel;

    private TextInputLayout name, description, type;
    private Button addProductBtn, cancelBtn;

    private int argPosition;
    private ProductModel currentProduct;
    private MaterialButton removeBtn;

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
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureViewModels();
        configureViews();

        int nbProducts = restaurantViewModel.getCurrentRestaurantProducts().getValue().size();

        // --- Sets interaction button
        addProductBtn.setOnClickListener(v -> restaurantViewModel.addNewProduct(currentUser.token, currentUser.user.id, getNewProduct()));
        cancelBtn.setOnClickListener(v -> Navigation.findNavController(rootView).popBackStack());

        restaurantViewModel.getCurrentRestaurantProducts().observe(getViewLifecycleOwner(), products -> {
            int state = restaurantViewModel.getState();
            restaurantViewModel.setState(Utility.STATE_UNDEFINED);

            if (state == Utility.STATE_SUCCESS) {

                // Get message to display
                if (nbProducts < restaurantViewModel.getCurrentRestaurantProducts().getValue().size())
                    Utility.getSuccessSnackbar(context, rootView,
                            getString(R.string.success_add_product),
                            Snackbar.LENGTH_SHORT).show();
                else
                    Utility.getWarningSnackbar(context, rootView,
                            getString(R.string.success_remove_product),
                            Snackbar.LENGTH_SHORT).show();

                Navigation.findNavController(rootView).popBackStack();

            } else if (state != Utility.STATE_UNDEFINED)
                Utility.getErrorSnackbar(context, rootView, getString(R.string.err_occurred), Snackbar.LENGTH_SHORT).show();
        });
    }

    /**
     * Configure simple Views
     */
    public void configureViews() {
        name = rootView.findViewById(R.id.product_name);
        description = rootView.findViewById(R.id.product_description);
        addProductBtn = rootView.findViewById(R.id.add_btn);
        cancelBtn = rootView.findViewById(R.id.cancel_btn);
        type = rootView.findViewById(R.id.product_type);
        configureTypeDropdownMenu();


        // --- Get specific product from is position if added in arguments
        if (getArguments() != null) {
            argPosition = getArguments().getInt("POSITION");
            currentProduct = restaurantViewModel.getCurrentRestaurantProducts().getValue().get(argPosition);

            removeBtn = rootView.findViewById(R.id.remove_btn);
            removeBtn.setVisibility(View.VISIBLE);
            removeBtn.setOnClickListener(v -> restaurantViewModel.removeProduct(
                    currentUser.token,
                    currentUser.user.id,
                    currentProduct.id)
            );

            name.getEditText().setText(currentProduct.name);
            description.getEditText().setText(currentProduct.description);
            type.getEditText().setText(currentProduct.type);
        }
    }


    /**
     * Configure all View Models
     */
    public void configureViewModels() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        currentUser = userViewModel.getCurrentUser().getValue();
        if (currentUser == null) startActivity(Utility.getLogoutIntent(context));

        restaurantViewModel = new ViewModelProvider(requireActivity()).get(RestaurantViewModel.class);
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
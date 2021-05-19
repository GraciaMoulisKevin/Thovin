package com.example.thovin.ui.cart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.adapters.MenuAdapter;
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.AuthResult;
import com.example.thovin.models.CartModel;
import com.example.thovin.models.MenuModel;
import com.example.thovin.models.ProductResult;
import com.example.thovin.viewModels.CartViewModel;
import com.example.thovin.viewModels.UserViewModel;

import java.util.ArrayList;

public class CartFragment extends Fragment implements RecycleViewOnClickListener {

    private View rootView;
    private Context context;

    // --- User
    private UserViewModel userViewModel;
    private AuthResult currentUser;

    // --- Cart
    private CartViewModel cartViewModel;

    private TextView emptyCartText;
    private LinearLayout interactionButtons;
    private Button goToPaymentBtn, dumpCartBtn;
    private RecyclerView menusRecyclerView;

    public CartFragment() {
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        context = getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureViews();
        configureViewModels();

        currentUser = userViewModel.getCurrentUser().getValue();

        cartViewModel.getCurrentCart().observe(getViewLifecycleOwner(), currentCart -> {
            if (currentCart != null && currentCart.getMenus() != null) {
                setProductsRecyclerView(currentCart.getMenus());
                goToPaymentBtn.setText(currentCart.getPrice());
            } else toggleDisplay();
        });

        goToPaymentBtn.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_nav_cart_to_nav_payment));

        dumpCartBtn.setOnClickListener(v -> cartViewModel.deleteCart(currentUser.token));
    }


    /**
     * Configure simple Views
     */
    public void configureViews() {
        emptyCartText = rootView.findViewById(R.id.empty_cart_txt);
        goToPaymentBtn = rootView.findViewById(R.id.go_to_payment_btn);
        dumpCartBtn = rootView.findViewById(R.id.dump_cart_btn);
        interactionButtons = rootView.findViewById(R.id.interaction_buttons);
        menusRecyclerView = rootView.findViewById(R.id.menu_products);
    }


    /**
     * Configure all View Models
     */
    public void configureViewModels() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

        cartViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
                Utility.toggleSpinner(getActivity(), isLoading));
    }

    /**
     * Set products recycler view
     */
    public void setProductsRecyclerView(ArrayList<MenuModel> menus) {
        menusRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        MenuAdapter menuAdapter = new MenuAdapter(context, menus, this);
        menusRecyclerView.setAdapter(menuAdapter);
    }

    /**
     * Toggle display to show empty card text, otherwise the cart items
     */
    public void toggleDisplay() {
        if (emptyCartText.getVisibility() == View.INVISIBLE) {
            emptyCartText.setVisibility(View.VISIBLE);
            interactionButtons.setVisibility(View.GONE);
            menusRecyclerView.setVisibility(View.GONE);
        } else {
            emptyCartText.setVisibility(View.GONE);
            interactionButtons.setVisibility(View.VISIBLE);
            menusRecyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onItemClick(int position, String tag) {
    }
}
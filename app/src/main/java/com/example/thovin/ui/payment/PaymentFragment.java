package com.example.thovin.ui.payment;

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

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.models.AuthResult;
import com.example.thovin.models.CartModel;
import com.example.thovin.models.CreditCardModel;
import com.example.thovin.models.OrderRequest;
import com.example.thovin.viewModels.CartViewModel;
import com.example.thovin.viewModels.OrderViewModel;
import com.example.thovin.viewModels.UserViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

public class PaymentFragment extends Fragment {

    private View rootView;
    private Context context;

    // --- User
    private UserViewModel userViewModel;
    private AuthResult user;

    // --- Cart
    private CartViewModel cartViewModel;
    private CartModel currentCart;

    // --- Orders
    private OrderViewModel orderViewModel;

    TextInputLayout cardNumbers;
    TextInputLayout expirationDate;
    TextInputLayout ccv;
    MaterialButton paymentBtn;

    public PaymentFragment() {
    }

    public static PaymentFragment newInstance() {
        return new PaymentFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        context = getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureViews();
        configureViewModels();

        paymentBtn.setOnClickListener(v -> {
            String cartID = cartViewModel.getCurrentCart().getValue().id;
            CreditCardModel creditCard = new CreditCardModel(
                    cardNumbers.getEditText().getText().toString(),
                    expirationDate.getEditText().getText().toString(),
                    ccv.getEditText().getText().toString());

            OrderRequest orderReq = new OrderRequest(cartID, creditCard);
            orderViewModel.postOrder(user.token, orderReq);
        });


        orderViewModel.getIsPaymentSuccess().observe(getViewLifecycleOwner(), isOk -> {
            if (isOk != null) {
                Utility.getSuccessSnackbar(context, view,
                        getString(R.string.command_success), Snackbar.LENGTH_SHORT).show();
                cartViewModel.deleteCart(user.token);
                orderViewModel.dumpIsPaymentSuccess();
                Navigation.findNavController(view).navigate(R.id.action_nav_payment_to_nav_history);
            }
            else
                Utility.getErrorSnackbar(context, view,
                        getString(R.string.err_occurred), Snackbar.LENGTH_SHORT).show();
        });
    }

    /**
     * Configure simple Views
     */
    public void configureViews() {
        cardNumbers = rootView.findViewById(R.id.card_numbers);
        expirationDate = rootView.findViewById(R.id.expiration_date);
        ccv = rootView.findViewById(R.id.ccv);
        paymentBtn = rootView.findViewById(R.id.pay_btn);
    }

    /**
     * Configure all View Models
     */
    public void configureViewModels() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        user = userViewModel.getCurrentUser().getValue();

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);

        // --- Loading spinner
        orderViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
                Utility.toggleSpinner(getActivity(), isLoading));

        cartViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
                Utility.toggleSpinner(getActivity(), isLoading));
    }
}
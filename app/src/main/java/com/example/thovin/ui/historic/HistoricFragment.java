package com.example.thovin.ui.historic;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thovin.R;
import com.example.thovin.adapters.OrderAdapter;
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.OrderResult;
import com.example.thovin.viewModels.OrderViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class HistoricFragment extends Fragment implements RecycleViewOnClickListener {

    private View rootView;
    private Context context;

    private OrderViewModel orderViewModel;

    private ArrayList<OrderResult> historic;

    private RecyclerView orders;

    private TextView noPendingOrderText;
    private MaterialCardView currentOrderCardView;
    private TextView currentStatus;
    private TextView currentPrice;

    public HistoricFragment() {
    }

    public static HistoricFragment newInstance() {
        return new HistoricFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_historic, container, false);
        context = getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureViews();

        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);

        orderViewModel.getHistoric().observe(getViewLifecycleOwner(), historic -> {
            if (historic != null) {
                this.historic = historic;
                if (historic.size() > 0) setHistoricRecyclerView();
            }
        });

        if (orderViewModel.getCurrentOrder().getValue() == null) showAnyPendingOrderText(true);
        orderViewModel.getCurrentOrder().observe(getViewLifecycleOwner(), currentOrder -> {
            showAnyPendingOrderText(false);
            currentStatus.setText("Status: "+currentOrder.status);
            currentPrice.setText("66.66");
        });
    }

    public void configureViews() {
        noPendingOrderText = rootView.findViewById(R.id.no_pending_order);
        currentOrderCardView = rootView.findViewById(R.id.current_order);
        currentStatus = rootView.findViewById(R.id.pending_order_status);
        currentPrice = rootView.findViewById(R.id.pending_order_price);
        orders = rootView.findViewById(R.id.order_history);
    }


    public void setHistoricRecyclerView() {
        orders.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        OrderAdapter orderAdapter = new OrderAdapter(context, historic, this);
        orders.setAdapter(orderAdapter);
    }

    public void showAnyPendingOrderText(boolean state) {
        if (state) {
            currentOrderCardView.setVisibility(View.GONE);
            noPendingOrderText.setVisibility(View.VISIBLE);
        }
        else {
            currentOrderCardView.setVisibility(View.VISIBLE);
            noPendingOrderText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(int position) {
    }
}
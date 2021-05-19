package com.example.thovin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thovin.R;
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.OrderResult;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    Context context;
    ArrayList<OrderResult> orders;
    private final RecycleViewOnClickListener recycleViewOnClickListener;

    public OrderAdapter(Context context, ArrayList<OrderResult> orders, RecycleViewOnClickListener recycleViewOnClickListener) {
        this.context = context;
        this.orders = orders;
        this.recycleViewOnClickListener = recycleViewOnClickListener;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView container;
        private TextView text;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.adapter_history_container);
            text = itemView.findViewById(R.id.ENORME);
            container.setOnClickListener(v ->
                    recycleViewOnClickListener.onItemClick(getAdapterPosition(),
                            RecycleViewOnClickListener.TAG_ORDER));
        }
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_order, parent, false);
        return new OrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        OrderResult order = orders.get(position);
        holder.text.setText(order.getClient().getFullName());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
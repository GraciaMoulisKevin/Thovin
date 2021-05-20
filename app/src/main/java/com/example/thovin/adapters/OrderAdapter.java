package com.example.thovin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thovin.R;
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.MenuModel;
import com.example.thovin.models.OrderResult;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.LinkedList;

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
        private TextView status;
        private TextView clientName;
        private TextView clientPhone;
        private TextView clientAdresse;
        private TextView restaurantName;
        private TextView restaurantPhone;
        private TextView restaurantAddress;
        private TextView menus1;
        private TextView menus2;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.adapter_history_container);
            status = itemView.findViewById(R.id.order_status);
            clientName = itemView.findViewById(R.id.client_full_name);
            clientPhone = itemView.findViewById(R.id.client_phone);
            clientAdresse = itemView.findViewById(R.id.client_address);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantPhone = itemView.findViewById(R.id.restaurant_phone);
            restaurantAddress = itemView.findViewById(R.id.restaurant_address);
            menus1 = itemView.findViewById(R.id.menus1);
            menus2 = itemView.findViewById(R.id.menus2);

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

        setColor(holder, order);
        holder.status.setText(order.getStatus());

        holder.clientName.setText(order.getClient().getFullName());
        holder.clientPhone.setText(order.getClient().getPhone());
        holder.clientAdresse.setText(order.getClient().getAddress().getStreet());

        holder.restaurantName.setText(order.getRestaurant().getRestaurantName());
        holder.restaurantPhone.setText(order.getRestaurant().getPhone());
        holder.restaurantAddress.setText(order.getRestaurant().getFullAddress());

        LinkedList<String> tmp = displayMenu(order.getMenus());
        holder.menus1.setText(tmp.get(0));
        holder.menus2.setText(tmp.get(1));
    }

    private LinkedList<String> displayMenu(ArrayList<MenuModel> menus) {
        LinkedList<String> res = new LinkedList<String>();
        String res1 = "", res2 = "";
        for (int i = 0; i < menus.size(); i++) {
            if (i % 2 == 0) res1 += menus.get(i).getName() + "\n";
            else res2 += menus.get(i).getName() + "\n";
        }
        res.add(res1);
        res.add(res2);
        return res;
    }

    private void setTextViewDrawableColor(@NonNull OrderViewHolder holder, int color) {
        for (Drawable drawable : holder.status.getCompoundDrawablesRelative()) {
            if (drawable != null) {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
        }
    }

    private void setColor(@NonNull OrderViewHolder holder, OrderResult order) {
        switch (order.getStatus()) {
            case "waiting_for_payment":
                setTextViewDrawableColor(holder, context.getColor(R.color.red_700));
                break;
            case "pending":
                setTextViewDrawableColor(holder, context.getColor(R.color.main_yellow));
                break;
            case "ready":
                setTextViewDrawableColor(holder, context.getColor(R.color.main_yellow));
                break;
            case "accepted":
                setTextViewDrawableColor(holder, context.getColor(R.color.main_yellow));
                break;
            case "delivering":
                setTextViewDrawableColor(holder, context.getColor(R.color.main_yellow));
                break;
            case "delivered":
                setTextViewDrawableColor(holder, context.getColor(R.color.main_green));
                break;
            default:
                setTextViewDrawableColor(holder, Color.WHITE);
        }
    }

    private boolean hide(String status) {
        return status.equals("delivered") || status.equals("waiting_for_payment");
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
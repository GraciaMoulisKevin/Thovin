package com.example.thovin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        private TextView clientname;
        private TextView adresse;
        private TextView restaurantName;
        private TextView menus1;
        private TextView menus2;
        private TextView adresseAdd;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.adapter_history_container);
            clientname = itemView.findViewById(R.id.Clientname);
            adresse = itemView.findViewById(R.id.adresse);
            restaurantName = itemView.findViewById(R.id.RestaurantName);
            menus1 = itemView.findViewById(R.id.Menus1);
            menus2 = itemView.findViewById(R.id.Menus2);
            adresseAdd = itemView.findViewById(R.id.adresse_add);

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
        holder.clientname.setText(order.getClient().getFullName());
        holder.adresse.setText(order.getClient().getAddress().getStreet());
        holder.adresseAdd.setText(order.getClient().getAddress().getAdditional());
        holder.restaurantName.setText(order.getRestaurant().getRestaurantName());
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
        for (Drawable drawable : holder.clientname.getCompoundDrawablesRelative()) {
            if (drawable != null) {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            }
        }
    }

    private void setColor(@NonNull OrderViewHolder holder, OrderResult order) {
        switch (order.getStatus()) {
            case "waiting_for_payment":
                setTextViewDrawableColor(holder, Color.RED);
                break;
            case "pending":
                setTextViewDrawableColor(holder, Color.YELLOW);
                break;
            case "ready":
                setTextViewDrawableColor(holder, Color.rgb(39, 174, 96));
                break;
            case "accepted":
                setTextViewDrawableColor(holder, Color.rgb(34, 153, 84));
                break;
            case "delivering":
                setTextViewDrawableColor(holder, Color.rgb(25, 111, 61));
                break;
            case "delivered":
                setTextViewDrawableColor(holder, Color.rgb(20, 90, 50));
                break;
            default:
                setTextViewDrawableColor(holder, Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
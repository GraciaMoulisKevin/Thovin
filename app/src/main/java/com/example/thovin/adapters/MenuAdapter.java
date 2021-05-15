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
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    Context context;
    ArrayList<String> menus;
    private final RecycleViewOnClickListener recycleViewOnClickListener;

    public MenuAdapter(Context context, ArrayList<String> menus, RecycleViewOnClickListener recycleViewOnClickListener) {
        this.context = context;
        this.menus = menus;
        this.recycleViewOnClickListener = recycleViewOnClickListener;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView container;

        private TextView name;
        private TextView description;
        private TextView price;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.adapter_menu_container);
            container.setOnClickListener(v ->
                    recycleViewOnClickListener.onItemClick(getAdapterPosition()));

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
        }
    }

    @NonNull
    @Override
    public MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_menu, parent, false);
        return new MenuAdapter.MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {

        // TODO: add the description
//        holder.name.setText("MOCK NAME");
//        holder.description.setText("MOCK DESCRIPTION");

        // TODO: get the price by adding all the products
        double total = 0;
//        for (ProductModel product : menus.getProduct) {
//            total += product.price;
//        }
        holder.price.setText(String.valueOf(total));
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }
}

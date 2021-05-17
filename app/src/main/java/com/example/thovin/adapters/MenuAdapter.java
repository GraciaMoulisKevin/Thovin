package com.example.thovin.adapters;

import android.content.Context;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thovin.R;
import com.example.thovin.Utility;
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.MenuModel;
import com.example.thovin.models.ProductResult;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    Context context;
    ArrayList<MenuModel> menus;
    private final RecycleViewOnClickListener recycleViewOnClickListener;

    public MenuAdapter(Context context, ArrayList<MenuModel> menus, RecycleViewOnClickListener recycleViewOnClickListener) {
        this.context = context;
        this.menus = menus;
        this.recycleViewOnClickListener = recycleViewOnClickListener;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView container;

        private TextView name;
        private TextView description;
        private TextView price;
        private ImageView icon;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.adapter_menu_container);
            container.setOnClickListener(v ->
                    recycleViewOnClickListener.onItemClick(getAdapterPosition()));

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            icon = itemView.findViewById(R.id.icon);
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
        MenuModel menu = menus.get(position);

        holder.name.setText(menu.name);
        holder.description.setText(menu.description);

        float total = 0;
        for (ProductResult product : menu.getProducts()) {
            total += product.getPrice();
        }
        holder.price.setText(String.valueOf(total));

        Picasso.with(context).load(menu.imgURL)
                .resize(80, 80)
                .error(R.drawable.app_logo)
                .into(holder.icon);
    }


    @Override
    public int getItemCount() {
        return menus.size();
    }
}

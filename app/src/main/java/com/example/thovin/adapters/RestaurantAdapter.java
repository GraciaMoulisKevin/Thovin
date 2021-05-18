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
import com.example.thovin.models.UserModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    Context context;
    ArrayList<UserModel> restaurants;
    private final RecycleViewOnClickListener recycleViewOnClickListener;

    public RestaurantAdapter(Context context, ArrayList<UserModel> restaurants, RecycleViewOnClickListener recycleViewOnClickListener) {
        this.context = context;
        this.restaurants = restaurants;
        this.recycleViewOnClickListener = recycleViewOnClickListener;
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView container;
        TextView name;
        TextView description;
        TextView address;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.adapter_restaurant_container);
            container.setOnClickListener(v -> recycleViewOnClickListener.onItemClick(getAdapterPosition()));

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            address = itemView.findViewById(R.id.address);
        }
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        UserModel restaurant = restaurants.get(position);
        holder.name.setText(restaurant.restaurantName);
        holder.description.setText(R.string.content_mock_description);
        holder.address.setText(restaurant.getFullAddress());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}

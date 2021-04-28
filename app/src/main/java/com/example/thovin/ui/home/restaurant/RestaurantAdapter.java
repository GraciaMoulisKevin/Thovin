package com.example.thovin.ui.home.restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thovin.R;
import com.example.thovin.models.user.UserModel;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    Context context;
    ArrayList<UserModel> restaurants;

    public RestaurantAdapter(Context context, ArrayList<UserModel> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        TextView restaurantName;
        TextView restaurantDescription;
        TextView restaurantAddress;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantName = itemView.findViewById(R.id.name);
            restaurantDescription = itemView.findViewById(R.id.description);
            restaurantAddress = itemView.findViewById(R.id.address);
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
        holder.restaurantName.setText(restaurant.restaurantName);
        holder.restaurantDescription.setText(restaurant.email);
        holder.restaurantAddress.setText(restaurant.getFullAddress());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}

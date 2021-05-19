package com.example.thovin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thovin.R;
import com.example.thovin.interfaces.RecycleViewOnClickListener;
import com.example.thovin.models.ProductModel;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductModelAdapter extends RecyclerView.Adapter<ProductModelAdapter.ProductModelViewHolder> {
    Context context;
    ArrayList<ProductModel> products;
    private final RecycleViewOnClickListener recycleViewOnClickListener;

    public ProductModelAdapter(Context context, ArrayList<ProductModel> products, RecycleViewOnClickListener recycleViewOnClickListener) {
        this.context = context;
        this.products = products;
        this.recycleViewOnClickListener = recycleViewOnClickListener;
    }

    public class ProductModelViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView container;

        private TextView name;
        private TextView description;
        private TextView price;
        private ImageView image;

        public ProductModelViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.adapter_menu_container);

            container.setOnClickListener(v -> recycleViewOnClickListener.onItemClick(getAdapterPosition()));

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.icon);
        }
    }

    @NonNull
    @Override
    public ProductModelAdapter.ProductModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adapter_menu, parent, false);
        return new ProductModelAdapter.ProductModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductModelViewHolder holder, int position) {
        ProductModel product = products.get(position);
        holder.name.setText(product.name);
        holder.description.setText(product.description);
        holder.price.setVisibility(View.GONE);

        Picasso.get().load(product.imgURL)
                .resize(80, 80)
                .error(R.drawable.app_logo)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}

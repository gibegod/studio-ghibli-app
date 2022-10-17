package com.example.studioghibliapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studioghibliapp.models.Location;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<Location> locations;
    private OnItemClickListener onItemClickListener;

    public LocationAdapter(List<Location> locations, OnItemClickListener onItemClickListener) {
        this.locations = locations;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLocation = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(itemLocation);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.tvLocationName.setText(locations.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(locations.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocationName;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocationName = itemView.findViewById(R.id.tv_location_name);
        }
    }

    interface OnItemClickListener {
        void onItemClickListener(Location location);
    }
}


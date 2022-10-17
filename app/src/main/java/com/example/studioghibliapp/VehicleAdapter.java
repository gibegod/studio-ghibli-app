package com.example.studioghibliapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studioghibliapp.models.Location;
import com.example.studioghibliapp.models.Vehicle;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder> {

    private List<Vehicle> vehicles;
    private OnItemClickListener onItemClickListener;

    public VehicleAdapter(List<Vehicle> vehicle, OnItemClickListener onItemClickListener) {
        this.vehicles = vehicle;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemVehicle = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicle, parent, false);
        return new VehicleViewHolder(itemVehicle);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        holder.tvVehicleName.setText(vehicles.get(position).getName());
        holder.tvVehicleClass.setText(vehicles.get(position).getVehicle_class());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(vehicles.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public class VehicleViewHolder extends RecyclerView.ViewHolder {
        TextView tvVehicleName;
        TextView tvVehicleClass;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVehicleName = itemView.findViewById(R.id.tv_vehicle_name);
            tvVehicleClass = itemView.findViewById(R.id.tv_vehicle_class);
        }
    }

    interface OnItemClickListener {
        void onItemClickListener(Vehicle vehicle);
    }
}


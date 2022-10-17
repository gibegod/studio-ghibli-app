package com.example.studioghibliapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studioghibliapp.models.Species;

import java.util.List;

public class SpeciesAdapter extends RecyclerView.Adapter<SpeciesAdapter.SpeciesViewHolder> {

    private List<Species> speciesList;
    private OnItemClickListener onItemClickListener;

    public SpeciesAdapter(List<Species> speciesList, OnItemClickListener onItemClickListener) {
        this.speciesList = speciesList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SpeciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemSpecies = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_species, parent, false);
        return new SpeciesViewHolder(itemSpecies);
    }

    @Override
    public void onBindViewHolder(@NonNull SpeciesViewHolder holder, int position) {
        holder.tvSpeciesName.setText(speciesList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(speciesList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return speciesList.size();
    }

    public class SpeciesViewHolder extends RecyclerView.ViewHolder {
        TextView tvSpeciesName;

        public SpeciesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSpeciesName = itemView.findViewById(R.id.tv_species_name);
        }
    }

    interface OnItemClickListener {
        void onItemClickListener(Species species);
    }
}


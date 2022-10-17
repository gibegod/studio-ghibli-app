package com.example.studioghibliapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studioghibliapp.models.People;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {

    private List<People> peopleList;
    private OnItemClickListener onItemClickListener;

    public PeopleAdapter(List<People> peopleList, OnItemClickListener onItemClickListener) {
        this.peopleList = peopleList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemPeople = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people, parent, false);
        return new PeopleViewHolder(itemPeople);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
        holder.tvName.setText(peopleList.get(position).getName());
        holder.tvAge.setText(peopleList.get(position).getAge());
        holder.tvGender.setText(peopleList.get(position).getGender());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(peopleList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public class PeopleViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAge;
        TextView tvGender;

        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAge = itemView.findViewById(R.id.tv_age);
            tvGender = itemView.findViewById(R.id.tv_gender);
        }
    }

    interface OnItemClickListener {
        void onItemClickListener(People people);
    }
}


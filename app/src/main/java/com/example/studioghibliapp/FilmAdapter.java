package com.example.studioghibliapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    private List<FilmModel> films;
    private OnItemClickListener onItemClickListener;

    public FilmAdapter(List<FilmModel> films, OnItemClickListener onItemClickListener) {
        this.films = films;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemFilm = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new FilmViewHolder(itemFilm);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        holder.tvTitle.setText(films.get(position).getTitle());
        holder.tvDirector.setText(films.get(position).getDirector());
        holder.tvReleaseDate.setText(films.get(position).getReleaseDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(films.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDirector;
        TextView tvReleaseDate;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDirector = itemView.findViewById(R.id.tv_director);
            tvReleaseDate = itemView.findViewById(R.id.tv_release_date);
        }
    }

    interface OnItemClickListener {
        void onItemClickListener(FilmModel film);
    }
}


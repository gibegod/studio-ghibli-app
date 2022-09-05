package com.example.studioghibliapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvFilms;
    FilmAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupAdapter();
    }

    private void setupAdapter() {
        rvFilms = findViewById(R.id.rv_films);
        adapter = new FilmAdapter(getFilms(), new FilmAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(FilmModel film) {
                // Funcionalidad
                Toast.makeText(MainActivity.this, film.getTitle(), Toast.LENGTH_LONG).show();
            }
        });
        rvFilms.setAdapter(adapter);
    }

    private List<FilmModel> getFilms() {
        return new ArrayList<FilmModel>() {{
            add(new FilmModel("1", "Castle in the Sky","https://image.tmdb.org/t/p/w600_and_h900_bestv2/npOnzAbLh6VOIu3naU5QaEcTepo.jpg" ,"The orphan Sheeta inherited a mysterious crystal that links her to the mythical sky-kingdom of Laputa. With the help of resourceful Pazu and a rollicking band of sky pirates, she makes her way to the ruins of the once-great civilization. Sheeta and Pazu must outwit the evil Muska, who plans to use Laputa's science to make himself ruler of the world."));
            add(new FilmModel("2", "Grave of the Fireflies", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/qG3RYlIVpTYclR9TYIsy8p7m7AT.jpg", "In the latter part of World War II, a boy and his sister, orphaned when their mother is killed in the firebombing of Tokyo, are left to survive on their own in what remains of civilian life in Japan. The plot follows this boy and his sister as they do their best to survive in the Japanese countryside, battling hunger, prejudice, and pride in their own quiet, personal battle."));
        }};
    }
}
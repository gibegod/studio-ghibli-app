package com.example.studioghibliapp

import com.example.studioghibliapp.models.Film
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StudioGhibliAPI {

    @GET("/films")
    fun getFilms(): Call<List<Film>>

    @GET("/films/{id}")
    fun getFilm(@Path("id") id: String): Call<Film>
}
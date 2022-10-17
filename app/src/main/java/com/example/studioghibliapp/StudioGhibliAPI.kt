package com.example.studioghibliapp

import com.example.studioghibliapp.models.Film
import com.example.studioghibliapp.models.Location
import com.example.studioghibliapp.models.People
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StudioGhibliAPI {

    @GET("/films")
    fun getFilms(): Call<List<Film>>

    @GET("/films/{id}")
    fun getFilm(@Path("id") id: String): Call<Film>

    @GET("/people")
    fun getPeople(): Call<List<People>>

    @GET("/locations")
    fun getLocations(): Call<List<Location>>
}
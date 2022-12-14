package com.example.studioghibliapp

import com.example.studioghibliapp.models.*
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

    @GET("/vehicles")
    fun getVehicles(): Call<List<Vehicle>>

    @GET("/species")
    fun getSpecies(): Call<List<Species>>
}
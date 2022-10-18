package com.example.studioghibliapp

import com.example.studioghibliapp.models.YesNo
import retrofit2.Call
import retrofit2.http.GET

interface YesNoAPI {

    @GET("/api")
    fun getYesOrNo(): Call<YesNo>
}
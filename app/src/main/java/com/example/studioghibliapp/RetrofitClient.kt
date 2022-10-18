package com.example.studioghibliapp

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private const val baseURL = "https://ghibliapi.herokuapp.com"
    private const val yesNoAPIBaseURL = "https://yesno.wtf/"

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val retrofitYesNoAPI: Retrofit = Retrofit.Builder()
        .baseUrl(yesNoAPIBaseURL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}
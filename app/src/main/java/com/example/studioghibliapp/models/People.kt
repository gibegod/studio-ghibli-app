package com.example.studioghibliapp.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class People (
    var id: String?,
    var name: String?,
    var gender: String?,
    var age: String?,
)

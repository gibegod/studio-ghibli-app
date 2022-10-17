package com.example.studioghibliapp.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Species (
    var id: String?,
    var name: String?,
    var classification: String?,
)
package com.example.studioghibliapp.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location (
    var id: String?,
    var name: String?,
)
package com.example.studioghibliapp.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Vehicle (
    var id: String?,
    var name: String?,
    var description: String?,
    var vehicle_class: String?,
)
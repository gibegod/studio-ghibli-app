package com.example.studioghibliapp.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YesNo (
    var answer: String,
    var forced: Boolean,
    var image: String,
)
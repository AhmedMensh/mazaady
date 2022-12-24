package com.example.mazaady.data.entities.categories


import com.squareup.moshi.Json

data class PropertiesResponse(
    @field:Json(name = "data")
    val `data`: List<PropertyResponse?>? = null
)
package com.example.mazaady.data.entities.categories


import com.squareup.moshi.Json

data class CategoriesResponse(
    @field:Json(name = "data")
    val `data`: Data? = null
) {
    data class Data(
        @field:Json(name = "categories")
        val categories: List<CategoryResponse?>? = null
    )
}
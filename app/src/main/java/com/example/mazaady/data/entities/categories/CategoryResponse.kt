package com.example.mazaady.data.entities.categories

import com.example.mazaady.domain.models.CategoryModel
import com.example.mazaady.domain.models.SubCategoryModel
import com.squareup.moshi.Json

data class CategoryResponse(
    @field:Json(name = "children")
    val children: List<SubCategoryResponse?>? = null,
    @field:Json(name = "circle_icon")
    val circleIcon: String? = null,
    @field:Json(name = "description")
    val description: String? = null,
    @field:Json(name = "disable_shipping")
    val disableShipping: Int? = null,
    @field:Json(name = "id")
    val id: Int? = null,
    @field:Json(name = "image")
    val image: String? = null,
    @field:Json(name = "name")
    val name: String? = null,
    @field:Json(name = "slug")
    val slug: String? = null
)

fun CategoryResponse.toDomain() = CategoryModel(
    id = id ?: -1,
    circleIcon = circleIcon.orEmpty(),
    description = description.orEmpty(),
    disableShipping = disableShipping ?: -1,
    image = image.orEmpty(),
    name = name.orEmpty(),
    slug = slug.orEmpty(),
    children = children.toDomain()
)

private fun List<SubCategoryResponse?>?.toDomain(): List<SubCategoryModel?>? {
    return this?.map { it?.toDomain() }
}



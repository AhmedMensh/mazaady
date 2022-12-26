package com.example.mazaady.data.entities.categories

import com.example.mazaady.domain.models.PropertyOptionModel
import com.squareup.moshi.Json

class PropertyOptionResponse(
    @field:Json(name = "child")
    val child: Boolean? = null,
    @field:Json(name = "id")
    val id: Int? = null,
    @field:Json(name = "name")
    val name: String? = null,
    @field:Json(name = "parent")
    val parent: Int? = null,
    @field:Json(name = "slug")
    val slug: String? = null
)

fun PropertyOptionResponse.toDomain() = PropertyOptionModel(
        child = child ?: false,
        id = id ?: -1,
        name = name.orEmpty(),
        parent = parent ?: -1,
        slug =slug.orEmpty()
)


fun MutableList<PropertyOptionResponse?>?.toDomain(): List<PropertyOptionModel?>? {
    this?.add(0,PropertyOptionResponse(
        id = -1,
        child = false,
        name = "other",
        parent = 0,
        slug = "",
        ))

    return this?.map { it?.toDomain() }
}

package com.example.mazaady.data.entities.categories

import com.example.mazaady.domain.models.PropertyModel
import com.squareup.moshi.Json

data class PropertyResponse(
    @field:Json(name = "description")
    val description: String? = null,
    @field:Json(name = "id")
    val id: Int? = null,
    @field:Json(name = "list")
    val list: Boolean? = null,
    @field:Json(name = "name")
    val name: String? = null,
    @field:Json(name = "options")
    val options: MutableList<PropertyOptionResponse?>? = null,
    @field:Json(name = "other_value")
    val otherValue: Any? = null,
    @field:Json(name = "parent")
    val parent: Any? = null,
    @field:Json(name = "slug")
    val slug: String? = null,
    @field:Json(name = "type")
    val type: String? = null,
    @field:Json(name = "value")
    val value: String? = null
)

fun PropertyResponse.toDomain() = PropertyModel(
    description = description.orEmpty(),
    id = id ?: -1,
    list = list ?: false,
    name = name.orEmpty(),
    slug = slug.orEmpty(),
    type = type.orEmpty(),
    value = value.orEmpty(),
    options = options.toDomain()
)


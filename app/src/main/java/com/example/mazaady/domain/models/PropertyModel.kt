package com.example.mazaady.domain.models

import com.example.mazaady.core.adapter.AdapterItem

class PropertyModel(
    val description: String,
    val id: Int,
    val list: Boolean,
    val name: String,
    var children : List<PropertyModel?> ? = null,
    val options: List<PropertyOptionModel?>? = null,
    val slug: String,
    val type: String,
    val value: String,
    var selectedOptionModel: PropertyOptionModel? = null
) : AdapterItem {
    override fun id(): String {
        return "$id"
    }

    fun hasOneOrLessOption() = (options?.size ?: 0) <= 1
}